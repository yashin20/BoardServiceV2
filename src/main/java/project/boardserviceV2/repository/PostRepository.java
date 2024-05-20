package project.boardserviceV2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.entity.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    //작성자 : member -> unknown
    @Modifying
    @Query("update Post p set p.member = :unknownMember where p.member.id = :memberId")
    void updateMemberToUnknownByMemberId(@Param("memberId") Long memberId, @Param("unknownMember") Member unknownMember);
}
