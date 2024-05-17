package project.boardserviceV2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {
    /**
     * 게시글 생성 DTO
     */

    @NotNull(message = "title - 값이 입력되지 않았습니다.")
    private String title;

    @NotNull(message = "content - 값이 입력되지 않았습니다.")
    private String content;
}
