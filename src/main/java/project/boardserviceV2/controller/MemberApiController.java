package project.boardserviceV2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.boardserviceV2.dto.CreateMemberDto;
import project.boardserviceV2.dto.UpdateMemberDto;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.service.MemberService;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    /**
     * 회원가입 API
     *
     * @param createMemberDto : 회원가입 요청 양식
     * @return : 회원가입 회원
     */
    @PostMapping("/join")
    public ResponseEntity<?> joinMember(@RequestBody CreateMemberDto createMemberDto) {
        Member newMember = memberService.join(createMemberDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }


    /**
     * 회원정보 요청 API
     *
     * @param memberId : 조회하고자 하는 회원 ID
     * @return : 회원 정보
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<?> memberInfo(@PathVariable Long memberId) {
        Member member = memberService.findMemberById(memberId);
        return ResponseEntity.ok(member);
    }


    /**
     * 회원정보 수정 API
     *
     * @param memberId : 수정 회원 ID
     * @return : 수정 회원 정보
     */
    @PutMapping("/{memberId}")
    public ResponseEntity<?> updateMemberInfo(@PathVariable Long memberId, @RequestBody UpdateMemberDto updateMemberDto) {
        //회원 정보 수정 후 회원 반환
        Member member = memberService.updateMember(memberId, updateMemberDto);
        return ResponseEntity.ok(member);
    }


    /**
     * 회원 삭제 API
     *
     * @param memberId : 삭제 회원 ID
     * @return : 삭제 회원 ID
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable Long memberId) {
        Long deleteMemberId = memberService.deleteMember(memberId);
        return ResponseEntity.ok(deleteMemberId);
    }
}
