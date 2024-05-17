package project.boardserviceV2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "닉네임 - 값이 입력되지 않았습니다.")
    @Size(min = 2, max = 10, message = "닉네임 - 2~10자로 구성되어야 합니다.")
    private String nickname;

    @NotBlank(message = "비밀번호 - 값이 입력되지 않았습니다.")
    @Size(min = 8, max = 16, message = "비밀번호 - 8~16자로 구성되어야 합니다.")
    private String password;
    // pfp
}
