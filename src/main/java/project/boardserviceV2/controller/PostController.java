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
        model.addAttribute("createPostDto", new CreatePostDto());
        return "post/createPost";
    }

    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute CreatePostDto createPostDto, BindingResult bindingResult,
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

        postService.createPost(createPostDto, member);

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
        UpdatePostDto updatePostDto = new UpdatePostDto(post.getTitle(), post.getContent());

        model.addAttribute("updatePostDto", updatePostDto);
        return "post/updatePost";
    }

    @PostMapping("/{postId}/update")
    public String updatePost(@PathVariable Long postId, @Valid @ModelAttribute UpdatePostDto updatePostDto,
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

        postService.updatePost(postId, updatePostDto);
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


    /**
     * ===============================================
     * 페이징 테스트
     */
    @GetMapping("/paging-test") /* size = 10(default), page = 0(default) */
    public String pagingTest(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable, Model model) {
        Page<Post> list = postService.pageList(pageable);

        model.addAttribute("posts", list);
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()); //이전 페이지 번호
        model.addAttribute("next", pageable.next().getPageNumber()); //다음 페이지 번호
        model.addAttribute("hasNext", list.hasNext()); //다음 페이지 존재 여부
        model.addAttribute("hasPrevious", list.hasPrevious()); //이전 페이지 존재 여부

        //현재 페이지 정보 (사용자 기준으로 넘겨줌)
        int currentPage = pageable.getPageNumber() + 1;
        model.addAttribute("current", currentPage);

        /** 페이지 블록 계산
         * currentPage = 5
         * User : 5 , Spring : 4
         * startPage = 1
         * endPage = 5
         * <= 1 2 3 4 5 =>
         */

        int blockSize = 5;
        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1; //블럭의 시작 페이지
        int endPage = Math.min(startPage + blockSize - 1, list.getTotalPages()); //블럭의 마지막 페이지

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "test/pagingTestPage";
    }
    /**
     *
     =================================================*/


    /**
     * 게시글 키워드 검색
     *//*
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model,
                         @PageableDefault(sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        Page<Post> searchList = postService.search(keyword, pageable);
        Page<PostInfoDto> posts = searchList.map(PostInfoDto::new);

        model.addAttribute("posts", posts);
        model.addAttribute("keyword", keyword); //검색 키워드
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber()); //이전 페이지 정보
        model.addAttribute("next", pageable.next().getPageNumber()); //다음 페이지 정보
        model.addAttribute("hasPrevious", searchList.hasPrevious()); //이전 페이지 존재 여부
        model.addAttribute("hasNext", searchList.hasNext()); //다음 페이지 존재 여부


        *//** 페이지 블록 계산
         * currentPage = 5
         * User : 5 , Spring : 4
         * startPage = 1
         * endPage = 5
         * <= 1 2 3 4 5 =>
         *//*
        int currentPage = pageable.getPageNumber() + 1; //현재 페이지 정보(User side)
        model.addAttribute("current", currentPage);

        int blockSize = 5;
        int startPage = ((currentPage - 1) / blockSize) * blockSize + 1; //블럭 시작 페이지
        int endPage = Math.min(startPage + blockSize - 1, searchList.getTotalPages()); //블럭 마지막 페이지


        model.addAttribute("startPage", startPage); //페이지 블럭 시작 페이지
        model.addAttribute("endPage", endPage); //페이지 블럭 마지막 페이지

        return "post/search";
    }*/
}
