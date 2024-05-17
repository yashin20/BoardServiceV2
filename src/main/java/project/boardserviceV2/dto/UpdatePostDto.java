package project.boardserviceV2.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostDto {
    /**
     * 게시글 수정 DTO
     */

    @NotNull(message = "title - 값이 입력되지 않았습니다.")
    private String title;

    @NotNull(message = "content - 값이 입력되지 않았습니다.")
    private String content;
}
