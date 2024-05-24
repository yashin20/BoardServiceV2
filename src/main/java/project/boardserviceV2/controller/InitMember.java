package project.boardserviceV2.controller;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.boardserviceV2.entity.Member;
import project.boardserviceV2.entity.Post;

import java.time.LocalDateTime;

// Test Data 생성
@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitUserService initUserService;

    @PostConstruct
    public void init() {
        initUserService.init();
    }


    @Component
    static class InitUserService {
        @PersistenceContext
        private EntityManager em;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Transactional
        public void init() {
            /**
             * !!회원 삭제를 대비한 "알수없음" 회원!! (필수)
             */
            Member unknown = new Member("unknown", "12345678",
                    "unknown","unknown@unknown");
            em.persist(unknown);


            // User 100ea
            for (int i = 1; i <= 10; i++) {
                Member member = new Member("Member" + i, "12345678",
                        "Mem" + i ,"Member" + i + "@example.ac.kr");
                em.persist(member);
            }

            Member member1 = new Member("Member17", "12345678", "Mem17", "exam@exam.com");
            em.persist(member1);


            Post post1 = new Post();
            post1.setTitle("comment test");
            post1.setContent("this is comment test!");
            post1.setMember(member1);
            post1.setCreatedAt(LocalDateTime.now());
            post1.setUpdatedAt(LocalDateTime.now());
            post1.setView(10);
            em.persist(post1);

            Post post2 = new Post();
            post2.setTitle("comment test2");
            post2.setContent("this is comment test!2");
            post2.setMember(member1);
            post2.setCreatedAt(LocalDateTime.of(2024,5,10,10,0));
            post2.setUpdatedAt(LocalDateTime.now());
            post2.setView(20);
            em.persist(post2);

            for (int i = 1; i <= 100; i++) {
                Post post = new Post();
                post.setTitle("title" + i);
                post.setContent("this is content" + i);
                post.setMember(member1);
                post.setCreatedAt(LocalDateTime.of(2024,5,(i%30)+1,10,0));
                post.setUpdatedAt(LocalDateTime.now());
                post.setView(0);
                em.persist(post);
            }
        }
    }
}
