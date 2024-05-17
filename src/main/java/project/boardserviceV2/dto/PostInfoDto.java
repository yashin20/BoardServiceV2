package project.boardserviceV2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.boardserviceV2.entity.Post;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostInfoDto {

    /**
     * 게시글 정보 DTO
     * post_id : Primary Key
     * title : 게시글 제목
     * content : 게시글 내용
     * view : 조회수
     * member : 작성자 (nickname)
     * createdAt : 생성일자
     * updatedAt : 수정일자
     *
     * username : '작성자 == 로그인 사용자' 확인을 위함.
     */

    private Long postId;
    private String title;
    private String content;
    private Integer view;
    private String member; // 작성자
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String username; // '작성자 == 로그인 사용자' 확인을 위함.

    /**
     * 생성자
     * @param entity : post 객체
     */
    public PostInfoDto(Post entity) {
        this.postId = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.view = entity.getView();
        this.member = entity.getMember().getNickname();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();

        // '작성자 == 로그인 사용자' 확인을 위함.
        this.username = entity.getMember().getUsername();
    }
}
