package project.boardserviceV2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor // initMember 때문에
@ToString
public class Post extends BaseEntity{

    /**
     * post_id : Primary Key
     * title : 게시글 제목
     * content : 게시글 내용
     * view : 조회수
     *
     * member : 작성자
     * comments : List<comment> 댓글 목록
     *
     * createdAt : 생성일자
     * updatedAt : 수정일자
     */

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    private Integer view = 0; //조회수 - 기본값 : 0 설정

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; //작성자

    // 게시글 삭제 시 - 해당 게시글의 댓글도 삭제
    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<Comment> comments = new ArrayList<>(); //댓글 목록

    // Post 생성자
    public Post(String title, String content, Member member) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    //Post 수정을 위한 함수
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
        this.setUpdatedAt(LocalDateTime.now());
    }

    //조회수 증가
    public void incrementView() {
        this.view += 1;
    }


    //Test Data 생성
    public Post(String title, String content, Integer view, Member member, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.content = content;
        this.view = view;
        this.member = member;
        this.setCreatedAt(createdAt);
        this.setUpdatedAt(updatedAt);
    }


}
