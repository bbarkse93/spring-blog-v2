package shop.mtcoding.blogv2.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Test
    public void findAll_test() {
        System.out.println("조회 직전");
        List<Board> boardList = boardRepository.findAll();

        System.out.println("조회 후 : Lazy");
        // 행 : 5개 -> 객체 : 5개
        // LAZY
        // 각 행: Board (id = 1, title = "제목1", content = "내용1", created_at = 날짜,
        // user(id=1))
        System.out.println(boardList.get(0).getId()); // 1번 (조회X)
        System.out.println(boardList.get(0).getUser().getId()); // 1번(조회X)
        System.out.println(boardList.get(0).getUser().getUsername()); // null -> ssar(조회 O)
    }

    @Test
    public void mFindAll_test() {
        boardRepository.mFindAll();
    }

    @Test
    public void findAll_paging_test() throws JsonProcessingException {
        Pageable pageable = PageRequest.of(0, 3, Sort.Direction.DESC, "id");
        Page<Board> boardPG = boardRepository.findAll(pageable);
        System.out.println(boardPG);
        ObjectMapper om = new ObjectMapper();
        // ObjectMapper는 boardPG 객체의 getter를 호출하면서 JSON을 만든다
        String json = om.writeValueAsString(boardPG); // 자바객체를 JSON으로 변환
        System.out.println(json);
    }
}
