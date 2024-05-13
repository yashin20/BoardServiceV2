package project.boardserviceV2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
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

}
