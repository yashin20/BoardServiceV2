package project.boardserviceV2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMemberDto {
    /**
     * 회원 정보 수정 DTO
     * 'nickname' , 'password' , 'pfp' 수정 가능
     */
    private String nickname;
    private String password;
    // pfp
}
