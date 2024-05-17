package project.boardserviceV2.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.boardserviceV2.dto.CreateMemberDto;
import project.boardserviceV2.dto.LoginDto;
import project.boardserviceV2.dto.MemberInfoDto;
import project.boardserviceV2.dto.PostInfoDto;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.service.MemberService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입
     */
    // Create New User
    @GetMapping("/new")
    public String createMemberForm(Model model) {
        //회원가입 폼 보내기
        model.addAttribute("createMemberDto", new CreateMemberDto());
        return "member/createMember";
    }

    @PostMapping("/new")
    public String join(@Valid @ModelAttribute CreateMemberDto createMemberDto, BindingResult bindingResult, Model model) {

        //유효성 검사 오류 시, 에러 처리 로직
        if (bindingResult.hasErrors()) {
            //에러 메시지 반환
            List<String> errorMessage = bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("errorMessage", errorMessage);
            return "member/createMember";
        }

        //회원가입
        memberService.join(createMemberDto);
        return "redirect:/";
    }

    /**
     * 로그인
     */
    // User Login
    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "member/login";
    }

    //로그인 관련 로직은 "/login" spring security 에게 이관한다.


    /**
     * 회원 정보
     */
    // User Information
    @GetMapping("/private/userInfo")
    public String getUserInfo(Principal principal, Model model) {
        String loginUsername = principal.getName(); //로그인한 회원 - username

        // 회원 정보
        Member member = memberService.findMemberByUsername(loginUsername); //로그인한 회원
        model.addAttribute("memberInfo", new MemberInfoDto(member.getUsername(),
                member.getEmail(), member.getNickname(), member.getCreatedAt(), member.getUpdatedAt()));

        // 회원이 작성한 게시글 목록
        List<PostInfoDto> posts = member.getPosts().stream()
                .map(post -> new PostInfoDto(post))
                .collect(Collectors.toList());
        model.addAttribute("posts", posts);

        return "member/memberInfo";
    }


    /**
     * 회원 정보 수정
     */
    //User Data Update


}
