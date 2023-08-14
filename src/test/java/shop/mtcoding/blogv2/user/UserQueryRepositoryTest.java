package shop.mtcoding.blogv2.user;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserQueryRepository.class)
@DataJpaTest // JpaRepository만 Ioc 메모리에 올려준다
public class UserQueryRepositoryTest {

    @Autowired
    private UserQueryRepository userQueryRepository;

    @Autowired
    private EntityManager em;

    // persist(영속화)
    @Test
    public void save_test() {
        User user = User.builder()
                .username("love")
                .password("1234")
                .email("love@naver.com")
                .build();
        userQueryRepository.save(user); // 영속화
    }

    // 1차 캐시
    @Test
    public void findById_test() {

        System.out.println("1. PC는 비어있다.");
        userQueryRepository.findById(1);
        System.out.println("2. PC는 user 1의 객체가 영속화 되어 있다.");
        em.clear(); // PC에 영속화된 객체를 날려버림
        userQueryRepository.findById(1);
    }

    @Test
    public void update_test() {
        // JPA update 알고리즘
        // 1. 업데이트 할 객체를 영속화
        // 2. 객체 상태 변경
        // 3. em.flush() or Transactional 정상종료(commit)
        User user = userQueryRepository.findById(1);
        System.out.println("변경 전 email: " + user.getEmail());
        user.setEmail("ssarmango@nate.com");
        em.flush();
        System.out.println("변경 후 email: " + user.getEmail());
    } // rollback
}
