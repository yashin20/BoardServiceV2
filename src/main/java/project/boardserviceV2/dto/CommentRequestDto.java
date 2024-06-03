package project.boardserviceV2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.boardserviceV2.entity.Comment;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.entity.Post;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    /**
     * Comment 작성 DTO
     * <p>
     * 등록 요청 : content, member, post
     * <p>
     * 조회 요청 : id
     * <p>
     * 수정 요청 : id, content
     * <p>
     * 삭제 요청 : id
     */
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Post post;
    private Member member;

/*
    //DTO -> Entity
    public Comment toEntity(CommentRequestDto dto) {
        Comment comment = new Comment(id, content, member, post);
        comment.setCreatedAt(createdAt);
        comment.setUpdatedAt(updatedAt);
        return comment;
    }
*/

}
