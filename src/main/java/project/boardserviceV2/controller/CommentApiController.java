package project.boardserviceV2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.boardserviceV2.dto.CommentRequestDto;
import project.boardserviceV2.service.CommentService;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;


    /**
     * 댓글 삭제 API
     *
     * @param commentId : 삭제 Comment's ID
     * @return : 삭제 Comment's ID
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return ResponseEntity.ok(commentId + "댓글이 삭제 되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 실패");
        }
    }

    /**
     * 댓글 수정 API
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto dto) {
        try {
            dto.setId(commentId);
            commentService.updateComment(dto);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"" + commentId + "댓글이 수정 되었습니다.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"댓글 수정 실패\"}");
        }
    }
}
