package project.boardserviceV2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.boardserviceV2.entity.Member;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MemberResponseDto {

    /**
     * 회원 응답 DTO
     * username
     * email
     * nickname
     * createdAt
     * updatedAt
     * pfp - 프로필 사진
     */

    private Long id;
    private String username;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    //Entity -> DTO
    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}
