package project.boardserviceV2.dto;

import lombok.Data;
import project.boardserviceV2.entity.Comment;

import java.time.LocalDateTime;

@Data
public class CommentResponseDto {

    /**
     * Comment 응답 DTO
     */

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long postId; //소속 게시글
    private Long memberId; //작성 회원

    private String username; //작성자 검증
    private String nickname; //작성자 표시

    private Boolean isEdited = false; //댓글의 수정됨 여부 (기본값 false)



    //Entity -> Dto
    public CommentResponseDto(Comment entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.postId = entity.getPost().getId();
        this.memberId = entity.getMember().getId();

        this.username = entity.getMember().getUsername();
        this.nickname = entity.getMember().getNickname();

        //Comment 의 수정됨 여부를 체크
        if (entity.getUpdatedAt() != null && !entity.getUpdatedAt().isEqual(entity.getCreatedAt())) {
            this.isEdited = true;
        }
    }
}
