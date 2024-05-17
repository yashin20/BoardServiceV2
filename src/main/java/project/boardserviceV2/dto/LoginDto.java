package project.boardserviceV2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    /**
     * 로그인 폼
     */
    private String username;
    private String password;
}
