package project.boardserviceV2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.boardserviceV2.dto.CommentRequestDto;
import project.boardserviceV2.dto.CommentResponseDto;
import project.boardserviceV2.entity.Comment;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.entity.Post;
import project.boardserviceV2.exception.DataNotFoundException;
import project.boardserviceV2.exception.UnauthorizedAccessException;
import project.boardserviceV2.repository.CommentRepository;
import project.boardserviceV2.repository.MemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository; // 작성자 -> unknown (회원 탈퇴)


    /**
     * Comment 생성
     * @param dto : CommentRequestDto
     * @return : 생성 Comment
     */
    @Transactional
    public Comment createComment(CommentRequestDto dto) {
        Comment comment = new Comment(dto.getContent(), dto.getMember(), dto.getPost());
        commentRepository.save(comment);

        return comment;
    }


    /**
     * Comment 목록 조회
     * @param post : 작성 대상 게시글
     * @return : List<CommentResponseDto>
     */
    public List<CommentResponseDto> getComments(Post post) {
        List<Comment> comments = commentRepository.findCommentsByPost(post);
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment))
                .collect(Collectors.toList());
    }


    /**
     * Comment 수정
     * @param dto : CommentRequestDto - 수정 양식
     * @return : 수정 Comment
     */
    @Transactional
    public Comment updateComment(CommentRequestDto dto) {
        //1. 수정 대상 Comment 찾기
        Optional<Comment> findComment = commentRepository.findById(dto.getId());
        if (!findComment.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 댓글입니다.");
        }
        Comment comment = findComment.get();

        //2. 사용자 검증
        String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!comment.getMember().getUsername().equals(loginName)) {
            throw new UnauthorizedAccessException("댓글 수정 권한이 없습니다.");
        }

        //3. 댓글 수정
        comment.update(dto.getContent());

        return comment;
    }


    /**
     * Comment 삭제
     * @param commentId : 삭제 대상 Comment's ID
     * @return : 삭제 대상 Comment's ID
     */
    @Transactional
    public Long deleteComment(Long commentId) {
        //1. 삭제 대상 Comment 찾기
        Optional<Comment> findComment = commentRepository.findById(commentId);
        if (!findComment.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 댓글입니다.");
        }
        Comment comment = findComment.get();

        //2. 사용자 검증
        String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!comment.getMember().getUsername().equals(loginName)) {
            throw new UnauthorizedAccessException("댓글 삭제 권한이 없습니다.");
        }

        //3. 삭제
        try {
            commentRepository.delete(comment);
        } catch (Exception e) {
            throw new RuntimeException("댓글 삭제 중 문제 발생");
        }
        return commentId;
    }


    /**
     * 회원 삭제 시, 작성한 Comment 의 작성자를 unknown 으로 변경
     */
    // 작성자 -> unknown 객체
    @Transactional
    public void updateCommentMemberToUnknown(Long memberId) {
        Member unknownMember = memberRepository.findByUsername("unknown").get();
        commentRepository.updateMemberToUnknownByMemberId(memberId, unknownMember);
    }
}
