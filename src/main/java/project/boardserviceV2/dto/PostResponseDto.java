package project.boardserviceV2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.boardserviceV2.entity.Post;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {

    /**
     * Post 응답 DTO
     * <p>
     * 등록 요청 - return : id
     * <p>
     * 조회 요청 - return : id, title, content, createdAt, updatedAt, view, memberNickname, memberUsername
     * <p>
     * 수정 요청 - return : id, title, content, createdAt, updatedAt, view, memberNickname, memberUsername
     * <p>
     * 삭제 요청 - return : id
     */

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer view;
    private String memberNickname;
    private String memberUsername;

    private String message; //예외 처리 메시지


    //등록 / 삭제 요청 return 생성자
    public PostResponseDto(Long id) {
        this.id = id;
    }


    //조회 / 수정 요청 return 생성자
    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.view = entity.getView();
        this.memberNickname = entity.getMember().getNickname();
        this.memberUsername = entity.getMember().getUsername();
    }

    // 오류 메시지용 생성자
    public PostResponseDto(String message) {
        this.message = message;
    }
}
