package shop.mtcoding.blogv2.board;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import shop.mtcoding.blogv2.user.User;

@DataJpaTest // 모든 Repository, EntityManger
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

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

    // 상세보기 test
    // @Test
    // public void findById_test() {
    // Optional<Board> boardOP = boardRepository.findById(5);
    // if (boardOP.isPresent()) {
    // System.out.println("테스트: board가 있습니다.");
    // }
    // }

    // 글수정 test
    @Test
    public void findById_test() {
        System.out.println("업데이트 테스트");
        Board board = boardRepository.findById(5).get();
        board.setTitle("제목 변경");
        board.setContent("내용 변경");
        em.flush();
        System.out.println("변경된 값");
        System.out.println(board.getTitle());
        System.out.println(board.getContent());
    }

    @Test
    public void mFindByIdJoinUserAndReplies_test() {
        Board board = boardRepository.mFindByIdJoinRepliesInUser(1).get();
        System.out.println("board id: " + board.getId());
        System.out.println("board title: " + board.getTitle());
        System.out.println("board content: " + board.getContent());
        System.out.println("board createdAt: " + board.getCreatedAt());
        System.out.println("=======================================");
        board.getReplies().stream().forEach(r -> {
            System.out.println("board in replies id: " + r.getId());
            System.out.println("board in replies comment: " + r.getComment());
            System.out.println("board in replies in user id: " + r.getUser().getId());
            System.out.println("board in replies in user username: " + r.getUser().getUsername());
        });

    }
}
