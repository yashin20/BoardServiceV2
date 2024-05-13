package project.boardserviceV2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberDto {
    /**
     * 회원 가입 DTO
     */
    private String username; //로그인 ID
    private String password;
    private String email;
    private String nickname; //사용할 닉네임
}
