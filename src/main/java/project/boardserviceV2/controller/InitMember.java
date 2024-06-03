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
import project.boardserviceV2.entity.Comment;
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
            Member unknown = new Member("unknown", passwordEncoder.encode("12345678"),
                    "unknown","unknown@unknown");
            em.persist(unknown);


            /**
             * Test Data
             * User 10ea (Member1 ~ Member10)
             * + Post 100ea (Member 당 10개)
             * + Comment 400ea (Post 당 4개)
             */

            //User 10ea (Member1 ~ Member10)
            for (int i = 1; i <= 10; i++) {
                Member member = new Member("Member" + i, passwordEncoder.encode("12345678"),
                        "Mem" + i ,"Member" + i + "@example.ac.kr");
                em.persist(member);

                //Post 100ea (Member 당 10개)
                for (int j = 1; j <= 10; j++) {
                    Post post = new Post("Post" + j + " by: Member" + i, "content" + j, member);
                    em.persist(post);

                    // Comment 250ea (Post 당 4개)
                    for (int k = 1; k <= 4; k++) {
                        Comment comment = new Comment("Comment" + k + " - belong to Post" + j + " write by Member" + i,
                                member, post);
                        em.persist(comment);
                    }
                }
            }

            Member member1 = new Member("Member77", passwordEncoder.encode("12345678"), "Mem77", "exam@exam.com");
            em.persist(member1);


            //검색 페이징을 위해 넣은 테스트 데이터
            for (int i = 1; i <= 20; i++) {
                int flag = i % 3;
                if (flag == 0) {
                    em.persist(new Post("Apple" + i, "Apple Content",(i + 1) * 70 ,member1,
                            LocalDateTime.of(2024, (i%11 + 1), i, 20, 20),
                            LocalDateTime.now()));
                } else if (flag == 1) {
                    em.persist(new Post("Banana" + i, "Banana Content",(i + 1) * 70, member1,
                            LocalDateTime.of(2024, (i%11 + 1), i, 20, 20),
                            LocalDateTime.now()));
                } else {
                    em.persist(new Post("AppBana" + i, "AppBana Content",(i + 1) * 70, member1,
                            LocalDateTime.of(2024, (i%11 + 1), i, 20, 20),
                            LocalDateTime.now()));
                }
            }
        }
    }
}
