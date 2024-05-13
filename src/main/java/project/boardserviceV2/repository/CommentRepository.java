package project.boardserviceV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.boardserviceV2.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
