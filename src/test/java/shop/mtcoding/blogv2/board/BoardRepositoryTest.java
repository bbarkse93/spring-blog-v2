package shop.mtcoding.blogv2.board;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest // 모든 Repository, EntityManger
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void save_test() {
        System.out.println("save_test");
        Board board = Board.builder()
                .title("제목")
                .content("내용")
                .user(User.builder().id(1).build())
                .build();

        System.out.println("id: " + board.getUser().getId());

        boardRepository.save(board);

        System.out.println("id: " + board.getUser().getId());
    }

    @Test
    public void findById_test() {
        Optional<Board> boardOP = boardRepository.findById(5);
        if (boardOP.isPresent()) {
            System.out.println("테스트: board가 있습니다.");
        }
    }
}
