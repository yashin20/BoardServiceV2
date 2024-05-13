package project.boardserviceV2.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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

    private Integer view = 0;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; //작성자

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>(); //댓글 목록
}
