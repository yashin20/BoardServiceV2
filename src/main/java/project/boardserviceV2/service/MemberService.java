package project.boardserviceV2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.boardserviceV2.dto.CreateMemberDto;
import project.boardserviceV2.dto.UpdateMemberDto;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.exception.DataAlreadyExistsException;
import project.boardserviceV2.exception.DataNotFoundException;
import project.boardserviceV2.exception.UnauthorizedAccessException;
import project.boardserviceV2.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //회원 삭제를 위함.
    private final PostService postService;
    private final CommentService commentService;

    /**
     * 회원가입
     * @param createMemberDto : 회원가입 양식
     * @return : 회원가입된 회원
     */
    @Transactional
    public Member join(CreateMemberDto createMemberDto) {
        //작성된 회원가입 양식 중복 검사
        duplicateValidation(createMemberDto);

        //member 생성 + 비밀번호 암호화
        Member member = new Member(createMemberDto.getUsername(),
                passwordEncoder.encode(createMemberDto.getPassword()), //비밀번호 암호화
                createMemberDto.getNickname(),
                createMemberDto.getEmail());

        memberRepository.save(member);
        return member;
    }

    // 인증 (로그인시 인증)
    public boolean authenticate(String name, String password) {
        Optional<Member> findMember = memberRepository.findByUsername(name);
        if (findMember.isPresent()) {
            return passwordEncoder.matches(password, findMember.get().getPassword());
        }
        return false;
    }

    //중복 체크 - 회원가입
    public void duplicateValidation(CreateMemberDto createMemberDto) {

        //username 중복 체크
        if (memberRepository.findByUsername(createMemberDto.getUsername()).isPresent()) {
            throw new DataAlreadyExistsException("이미 존재하는 ID 입니다.");
        }

        //email 중복 체크
        if (memberRepository.findByEmail(createMemberDto.getEmail()).isPresent()) {
            throw new DataAlreadyExistsException("이미 존재하는 이메일 입니다.");
        }

        //nickname 중복 체크
        if (memberRepository.findByNickname(createMemberDto.getNickname()).isPresent()) {
            throw new DataAlreadyExistsException("이미 존재하는 닉네임 입니다.");
        }
    }

    /**
     * 회원 검색 (username 기반)
     */
    public Member findMemberByUsername(String username) {
        Optional<Member> findMember = memberRepository.findByUsername(username);
        if (!findMember.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 회원 입니다.");
        }

        return findMember.get();
    }

    /**
     * 회원 검색 (id 기반)
     */
    public Member findMemberById(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        if (!findMember.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 회원 입니다.");
        }

        return findMember.get();
    }


    /**
     * 회원 삭제
     * @param memberId : 삭제 회원 ID
     * @return : 삭제 회원 ID
     */
    @Transactional
    public Long deleteMember(Long memberId) {
        //1. 삭제 대상 찾기
        Optional<Member> findMember = memberRepository.findById(memberId);

        //*예외처리 : 존재하는 회원인가?
        if (!findMember.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 회원 입니다.");
        }

        Member member = findMember.get(); //삭제 대상
        String loggedName = SecurityContextHolder.getContext().getAuthentication().getName(); //로그인된 username

        //*예외처리 : '삭제 회원'과 '로그인 회원'이 동일한가?
        if (!member.getUsername().equals(loggedName)) {
            throw new UnauthorizedAccessException("접근 권한이 없습니다.");
        }

        //2-1. 해당 회원의 작성한 post 의 작성자를 'unknown' 으로 변경
        postService.updatePostMemberToUnknown(memberId);
        //2-2. 해당 회원이 작성한 comment 의 작성자를 'unknown' 으로 변경
        commentService.updateCommentMemberToUnknown(memberId);

        //3. 회원 삭제
        memberRepository.delete(member);
        return memberId;
    }



    /**
     * 회원 정보 수정
     * @param memberId : 수정 회원 ID
     * @param dto : 수정 정보 - nickname, password
     * @return : 수정된 회원
     */
    @Transactional
    public Member updateMember(Long memberId, UpdateMemberDto dto) {
        //1. 수정 대상 찾기
        Optional<Member> findMember = memberRepository.findById(memberId);
        //*예외처리 : 존재하는 회원인가?
        if (!findMember.isPresent()) {
            throw new DataNotFoundException("존재하지 않는 회원 입니다.");
        }
        Member member = findMember.get(); //수정 대상

        //수정 닉네임 중복 체크 (이전 닉네임과 같지 않음 && 이미 존재하는 닉네임)
        if (!dto.getNickname().equals(member.getNickname())
                && memberRepository.findByNickname(dto.getNickname()).isPresent()) {
            throw new DataAlreadyExistsException("이미 존재하는 닉네임 입니다.");
        }

        String loggedName = SecurityContextHolder.getContext().getAuthentication().getName(); //로그인된 username

        //*예외처리 : 권한 확인 - " '삭제 회원'과 '로그인 회원'이 동일한가? "
        if (!member.getUsername().equals(loggedName)) {
            throw new UnauthorizedAccessException("접근 권한이 없습니다.");
        }

        //회원 정보 수정 (nickname, password)
        member.updateMemberInfo(dto.getNickname(),
                                passwordEncoder.encode(dto.getPassword()));

        //수정일자 업데이트
        member.setUpdatedAt(LocalDateTime.now());

        return member;
    }




}
