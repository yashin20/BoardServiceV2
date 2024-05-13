package project.boardserviceV2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.boardserviceV2.dto.CreateMemberDto;
import project.boardserviceV2.dto.UpdateMemberDto;
import project.boardserviceV2.entity.Member;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    public void joinMemberTest() throws Exception {
        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.setUsername("username1");
        createMemberDto.setPassword("password1");
        createMemberDto.setEmail("email1");
        createMemberDto.setNickname("nickname1");
        memberService.join(createMemberDto);


    }

    @Test
    @DisplayName("로그인 인증 테스트")
    public void loginTest() throws Exception {

        String rawPassword = "1234"; //암호화전 비밀번호

        //회원가입
        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.setUsername("username1");
        createMemberDto.setPassword(rawPassword);
        createMemberDto.setEmail("email1");
        createMemberDto.setNickname("nickname1");
        memberService.join(createMemberDto);

        //회원 검색 (username)
        Member member = memberService.findMemberByUsername("username1");


        //입력한 비밀번호 - 암호화된 비밀번호 가 서로 다름을 확인
        Assertions.assertNotEquals(rawPassword, member.getPassword());
        System.out.println("rawPassword = " + rawPassword);
        //rawPassword = 1234
        System.out.println("member.getPassword() = " + member.getPassword());
//        member.getPassword() = $2a$10$.ZtUAwHH0yJcix45AXJwxOt57hsXsVvYNh8UMLEHUxgzsQ6sxclTq

        // 로그인 시도 시 암호화된 비밀번호와 일치하는지 확인
        assertThat(memberService.authenticate(member.getUsername(), rawPassword)).isTrue();
    }

    @Test
    @DisplayName("회원정보 수정 테스트")
    public void updateMemberTest() throws Exception {
        //회원가입
        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.setUsername("username1");
        createMemberDto.setPassword("password1");
        createMemberDto.setEmail("email1");
        createMemberDto.setNickname("nickname1");
        memberService.join(createMemberDto);

        // 인증 정보 설정
        UserDetails userDetails = User.withUsername("username1")
                .password("password1")
                .roles("USER")
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        //회원 검색 (username)
        Member member = memberService.findMemberByUsername("username1");

        UpdateMemberDto updateMemberDto = new UpdateMemberDto();
        String rawPassword = "new password";
        updateMemberDto.setNickname("new nickname");
        updateMemberDto.setPassword(rawPassword);

        memberService.updateMember(member.getId(), updateMemberDto);


        assertThat(member.getNickname()).isEqualTo("new nickname"); //nickname 변경 확인
        // 로그인 시도 시 암호화된 비밀번호와 일치하는지 확인
        assertThat(memberService.authenticate(member.getUsername(), rawPassword)).isTrue(); //password 변경 확인
    }

}