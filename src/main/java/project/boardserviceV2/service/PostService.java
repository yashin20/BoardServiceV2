package project.boardserviceV2.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.boardserviceV2.dto.CreatePostDto;
import project.boardserviceV2.dto.PostInfoDto;
import project.boardserviceV2.dto.UpdatePostDto;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.entity.Post;
import project.boardserviceV2.exception.DataNotFoundException;
import project.boardserviceV2.repository.MemberRepository;
import project.boardserviceV2.repository.PostRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * 게시글 생성
     */
    @Transactional
    public Post createPost(CreatePostDto createPostDto, Member member) {
        Post post = new Post(createPostDto.getTitle(), createPostDto.getContent(), member);
        postRepository.save(post);

        return post;
    }

    /**
     * 게시글 목록
     */
    public Page<Post> pageList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }




    /**
     * 게시글 조회 (return Post)
     */
    public Post findPostById(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (!findPost.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 게시글 입니다.");
        }

        return findPost.get();
    }


    /**
     * 게시글 상세 정보 (return PostInfoDto)
     */
    @Transactional //조회수 증가
    public PostInfoDto getPostInfo(Long postId) {

        //1. Post 찾기
        Optional<Post> findPost = postRepository.findById(postId);
        if (!findPost.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 게시글 입니다.");
        }
        Post post = findPost.get();

        //2. 조회수 증가
        post.incrementView();

        return new PostInfoDto(post);
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Post updatePost(Long postId, UpdatePostDto updatePostDto) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (!findPost.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 게시글 입니다.");
        }

        Post post = findPost.get();
        post.update(updatePostDto.getTitle(), updatePostDto.getContent());
        return post;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public Long deletePost(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (!findPost.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 게시글 입니다.");
        }

        postRepository.delete(findPost.get());
        return postId;
    }


    /**
     * 회원 삭제 시, 작성한 Post 의 작성자를 unknown 으로 변경
     */
    // 작성자 -> unknown 객체
    @Transactional
    public void updatePostMemberToUnknown(Long memberId) {
        Member unknownMember = memberRepository.findByUsername("unknown").get();
        postRepository.updateMemberToUnknownByMemberId(memberId, unknownMember);
    }


    /**
     * 게시글 제목 검색
     */
    public Page<Post> search(String searchKeyword, Pageable pageable) {

        return postRepository.findByTitleContaining(searchKeyword, pageable);
    }


}
