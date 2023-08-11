package shop.mtcoding.blogv2.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest
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

        System.out.println("title: " + board.getTitle());
        System.out.println("content: " + board.getContent());
        System.out.println("id: " + board.getUser().getId());

        boardRepository.save(board);

        System.out.println("title: " + board.getTitle());
        System.out.println("content: " + board.getContent());
        System.out.println("id: " + board.getUser().getId());
    }
}
