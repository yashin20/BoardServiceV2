package project.boardserviceV2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.boardserviceV2.service.PostService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    /**
     * 게시글 삭제 API
     * @param postId : 삭제 게시글 ID
     * @return : 삭제 게시글 ID
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok(postId + "게시글이 삭제 되었습니다.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 삭제 실패");
        }
    }

    /**
     * 게시글 검색
    @GetMapping("/search")
    public ResponseEntity<?> searchPost() {

    }*/
}
