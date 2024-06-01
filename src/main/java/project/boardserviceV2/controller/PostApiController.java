package project.boardserviceV2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.boardserviceV2.dto.CommentRequestDto;
import project.boardserviceV2.dto.PostRequestDto;
import project.boardserviceV2.dto.PostResponseDto;
import project.boardserviceV2.entity.Post;
import project.boardserviceV2.service.PostService;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;


    /**
     * 게시글 등록 API
     * <p>
     * 요청 값 : title, content, member
     * 반환 값 : id
     */
    @PostMapping("/new")
    public ResponseEntity<PostResponseDto> createPostApi(@RequestBody PostRequestDto dto) {
        try {
            Post post = postService.createPost(dto);
            return ResponseEntity.ok(new PostResponseDto(post.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PostResponseDto("게시글 등록 실패"));
        }
    }


    /**
     * 게시글 조회 API
     * <p>
     * 요청 값 : id
     * 반환 값 : id, title, content, createdAt, updatedAt, member
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostApi(@PathVariable Long postId) {
        try{
            Post post = postService.findPostById(postId);
            return ResponseEntity.ok(new PostResponseDto(post));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PostResponseDto("게시글 조회 실패"));
        }
    }


    /**
     * 게시글 수정 API
     * <p>
     * 요청 값 : id, title, content
     * 반환 값 : id, title, content, createdAt, updatedAt, member
     */
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePostApi(@PathVariable Long postId, @RequestBody PostRequestDto dto) {
        try{
            dto.setId(postId);
            Post post = postService.updatePost(dto);
            return ResponseEntity.ok(new PostResponseDto(post));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PostResponseDto("게시글 수정 실패"));
        }
    }


    /**
     * 게시글 삭제 API
     * <p>
     * 요청 값 : id
     * 반환 값 : id
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable Long postId) {
        try {
            postService.deletePost(postId);
            return ResponseEntity.ok(new PostResponseDto(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PostResponseDto("게시글 삭제 실패"));
        }
    }
}
