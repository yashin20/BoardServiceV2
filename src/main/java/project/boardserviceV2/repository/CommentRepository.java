package project.boardserviceV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.boardserviceV2.entity.Comment;
import project.boardserviceV2.entity.Post;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPost(Post post);
}
