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
     * 게시글 목록 (return PostInfoDto)
     */
    public Page<PostInfoDto> getPostList(Pageable pageable) {
        int page = pageable.getPageNumber(); //현재 페이지 (페이지는 0 부터 시작)
        int size = 10; //페이지 당 보여줄 게시글 수

        Page<Post> posts = postRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));

        return posts.map(post -> new PostInfoDto(post));
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
    public PostInfoDto getPostInfo(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (!findPost.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 게시글 입니다.");
        }

        Post post = findPost.get();
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
        post.setTitle(updatePostDto.getTitle());
        post.setContent(updatePostDto.getContent());
        post.setUpdatedAt(LocalDateTime.now());

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
}
