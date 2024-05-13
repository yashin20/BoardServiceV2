package project.boardserviceV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.boardserviceV2.entity.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByUsername(String username);
    public Optional<Member> findByEmail(String email);
    public Optional<Member> findByNickname(String nickname);
}
