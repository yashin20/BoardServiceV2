package project.boardserviceV2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.boardserviceV2.entity.Member;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {

    /**
     * Post 요청 DTO
     *
     * 등록 요청 : title, content, member
     *
     * 조회 요청 : id
     *
     * 수정 요청 : id, title, content
     *
     * 삭제 요청 : id
     */

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Member member;

    //등록 요청 용 생성자
    public PostRequestDto(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
    }

    //수정 요청 용 생성자
    public PostRequestDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
