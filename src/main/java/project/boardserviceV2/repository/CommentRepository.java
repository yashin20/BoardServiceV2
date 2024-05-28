package project.boardserviceV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.boardserviceV2.entity.Comment;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.entity.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPost(Post post);


    //작성자 : member -> unknown
    @Modifying
    @Query("update Comment c set c.member = :unknownMember where c.member.id = :memberId")
    void updateMemberToUnknownByMemberId(@Param("memberId") Long memberId, @Param("unknownMember") Member unknownMember);
}
