package project.boardserviceV2.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.entity.Post;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(value = false)
class PostRepositoryTest {

    @Autowired PostRepository postRepository;
    @Autowired EntityManager em;


    @Test
    @DisplayName("게시글 제목 검색 테스트")
    public void findByTitleContainingTest() throws Exception {
        //given
        Member member = new Member("sample", "12345678", "nickname", "1111@1111");
        em.persist(member);

        for (int i = 0; i < 3; i++) {
            Post apple = new Post("apple" + (i + 1), "apple content", member);
            Post banana = new Post("banana" + (i + 1), "banana content", member);
            Post pizza = new Post("pizza" + (i + 1), "pizza content", member);
            em.persist(apple);
            em.persist(banana);
            em.persist(pizza);
        }
        Post contain = new Post("apple banana pizza", "all content", member);
        em.persist(contain);

        em.flush();
        em.clear();

        //when
        Page<Post> result = postRepository.findByTitleContaining("banana", Pageable.unpaged());

        //then
        Assertions.assertThat(result.getSize()).isEqualTo(4);

        for (Post post : result) {
            System.out.println("post = " + post.toString());
        }
    }

}