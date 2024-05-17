package project.boardserviceV2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto {

    /**
     * 회원 정보 DTO
     * username
     * email
     * nickname
     * createdAt
     * updatedAt
     * pfp - 프로필 사진
     */

    private String username;
    private String email;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //pfp
}
