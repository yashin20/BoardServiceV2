package project.boardserviceV2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import project.boardserviceV2.config.WebSecurityConfig;
import project.boardserviceV2.dto.CreateMemberDto;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.service.MemberService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MemberApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 API 테스트")
    public void joinApiTest() throws Exception {

        Member member = new Member("username1", "password1", "nickname1", "email@email");
        String json = objectMapper.writeValueAsString(member);

        mockMvc.perform(post("/api/members/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated()) // HTTP 201 응답 코드를 기대합니다.
                .andReturn();
    }

/*
    @Test
    @DisplayName("회원 정보 조회 API 테스트")
    public void memberInfoTest() throws Exception {
        CreateMemberDto createMemberDto = new CreateMemberDto("username1", "password1", "email@example.com", "nickname1");
        Member mockMember = new Member(1L, "username1", "password1", "email@example.com", "nickname1");

        memberService.join(createMemberDto);

        //API 요청 실행
        mockMvc.perform(get("/api/members/" + mockMember.getId())
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP 200 상태 코드 검증
                .andReturn();


        MvcResult result = mockMvc.perform(get("/api/members/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();


        System.out.println("================================");
        System.out.println("responseBody = " + responseBody);
        System.out.println("================================");


    }
    */
}

