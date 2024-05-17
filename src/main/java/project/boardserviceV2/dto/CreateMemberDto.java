package project.boardserviceV2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberDto {
    /**
     * 회원 가입 DTO
     */
    @NotBlank(message = "username - 값이 입력되지 않았습니다.")
    @Size(min = 2, max = 10, message = "username - 2~10자로 구성되어야 합니다.")
    private String username; //로그인 ID

    @NotBlank(message = "password - 값이 입력되지 않았습니다.")
    @Size(min = 8, max = 16, message = "password - 8~16자로 구성되어야 합니다.")
    private String password;

    @NotBlank(message = "email - 값이 입력되지 않았습니다.")
    private String email;

    @NotBlank(message = "nickname - 값이 입력되지 않았습니다.")
    @Size(min = 2, max = 10, message = "nickname - 2~10자로 구성되어야 합니다.")
    private String nickname; //사용할 닉네임
}
