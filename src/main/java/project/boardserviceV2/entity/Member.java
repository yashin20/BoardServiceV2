package project.boardserviceV2.entity;

import jakarta.persistence.*;
import lombok.*;
import project.boardserviceV2.dto.UpdateMemberDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity{

    /**
     * id : Primary Key
     * username : 로그인시 사용하는 ID
     * password : 비밀번호
     * nickname : 닉네임
     * userRole : 사용자 권한
     * email : 이메일
     *
     * posts : List<Post> - 작성한 게시글 리스트
     *
     * pfp : 프로필 사진
     * createdAt : 생성일자
     * updatedAt : 수정일자
     */

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String nickname;
    private String email;

    @OneToMany(mappedBy = "member") //Post 테이블에 있는 member 속성에 의해 매핑
    private List<Post> posts = new ArrayList<>();

//    private ImageType pfp; //profile picture


    public Member(String username) {
        this.username = username;
    }

    //MemberService join 에 사용
    public Member(String username, String password, String nickname, String email) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.userRole = UserRole.USER; //권한 - USER
        this.setCreatedAt(LocalDateTime.now()); //생성일자 - 현재
        this.setUpdatedAt(LocalDateTime.now()); //수정일자 - 현재
    }


    //회원가입 API 를 위한 생성자
    public Member(Long id, String username, String password, String nickname, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.userRole = UserRole.USER; //권한 - USER
        this.setCreatedAt(LocalDateTime.now()); //생성일자 - 현재
        this.setUpdatedAt(LocalDateTime.now()); //수정일자 - 현재
    }

    //updateMember 를 위한 Setter
    public void updateMemberInfo(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
