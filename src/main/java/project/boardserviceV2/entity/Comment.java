package project.boardserviceV2.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseEntity{

    /**
     * comment_id : Primary Key
     * content : 댓글 내용
     *
     * member : 작성자
     * post : 소속 게시글
     *
     * createdAt : 생성일자
     * updateAt : 수정일자
     */

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    //생성자
    public Comment(String content, Member member, Post post) {
        this.content = content;
        this.member = member;
        this.post = post;
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
    }

    //수정을 위한 Setter
    public void update(String content) {
        this.content = content; //내용 수정
        this.setUpdatedAt(LocalDateTime.now()); //수정 시간 갱신
    }
}
