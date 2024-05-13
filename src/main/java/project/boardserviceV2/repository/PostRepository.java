package project.boardserviceV2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.boardserviceV2.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {


}
