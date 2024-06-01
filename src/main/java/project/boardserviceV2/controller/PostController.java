package project.boardserviceV2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.boardserviceV2.dto.*;
import project.boardserviceV2.entity.Comment;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.entity.Post;
import project.boardserviceV2.service.CommentService;
import project.boardserviceV2.service.MemberService;
import project.boardserviceV2.service.PostService;

import java.util.List;
import java.util.stream.Collectors;


@RequestMapping("/post")
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final CommentService commentService;

    /**
     * 게시글 상세 정보 (+ 댓글)
     */
    @GetMapping("/{postId}")
    public String getPost(@PathVariable Long postId, Model model) {
        PostInfoDto postInfo = postService.getPostInfo(postId);

        //Post - 게시글 상세 정보
        model.addAttribute("postInfo", postInfo);

        //Comments - 댓글 리스트
        List<CommentResponseDto> comments = commentService.getComments(postService.findPostById(postId));
        model.addAttribute("comments", comments);

        //Create Comment - 댓글 작성 폼
        model.addAttribute("commentRequestDto", new CommentRequestDto());

        return "post/postInfo";
    }

    /**
     * 댓글 저장을 위한 Post 요청
     */
    @PostMapping("/{postId}")
    public String createComment(@PathVariable Long postId, @ModelAttribute CommentRequestDto dto,
                                Model model, Authentication authentication) {
        //dto 완성
        Post post = postService.findPostById(postId); //작성 게시글
        Member member = memberService.findMemberByUsername(authentication.getName()); //작성자
        dto.setPost(post);
        dto.setMember(member);

        //댓글 저장
        commentService.createComment(dto);

        return "redirect:/post/{postId}";
    }


    /**
     * 게시글 작성
     */
    @GetMapping("/new")
    public String getCreatePostForm(Model model) {
        model.addAttribute("postRequestDto", new PostRequestDto());
        return "post/createPost";
    }

    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute PostRequestDto dto, BindingResult bindingResult,
                             Model model, Authentication authentication) {
        //유효성 검사 오류 시, 에러 처리 로직
        if (bindingResult.hasErrors()) {
            //에러 메시지 반환
            List<String> errorMessage = bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("errorMessage", errorMessage);
            return "post/createPost";
        }

        //현재 로그인된 사용자 -> 작성자
        String loginName = authentication.getName();
        Member member = memberService.findMemberByUsername(loginName);

        dto.setMember(member);

        postService.createPost(dto);

        return "redirect:/";
    }


    /**
     * 게시글 수정
     */
    @GetMapping("/{postId}/update")
    public String getUpdatePostForm(@PathVariable Long postId, Model model, Authentication authentication) {
        String loginUsername = authentication.getName();
        Post post = postService.findPostById(postId);

        //*예외처리 : 현재 로그인된 사용자 != 작성자 (username 으로 판별)
        if (!loginUsername.equals(post.getMember().getUsername())) {
            return "redirect:/"; //main page 로 이동
        }

        //기존 내용 입력하기
        PostRequestDto postRequestDto = new PostRequestDto(postId, post.getTitle(), post.getContent());

        model.addAttribute("updatePostDto", postRequestDto);
        return "post/updatePost";
    }

    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, @Valid @ModelAttribute PostRequestDto dto,
                             BindingResult bindingResult, Model model) {

        //*예외처리 : UpdatePostDto 조건 만족
        if (bindingResult.hasErrors()) {
            //에러 메시지 반환
            List<String> errorMessage = bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("errorMessage", errorMessage);
            return "post/updatePost";
        }

        dto.setId(postId);
        postService.updatePost(dto);
        return "redirect:/";
    }


    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{postId}/delete")
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return "redirect:/";
    }
}
