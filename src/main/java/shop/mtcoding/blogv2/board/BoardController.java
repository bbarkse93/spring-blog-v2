package shop.mtcoding.blogv2.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// 1. 데이터 받기 (V)
// 2. 인증체크 (:TODO)
// 3. 유효성 검사 (:TODO)
// 4. 핵심로직 호출(서비스)
// 5. view or data 응답
@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) {
        boardService.글쓰기(saveDTO, 1);
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        List<Board> boardList = boardService.게시글목록보기();
        request.setAttribute("boardList", boardList);
        return "index";
    }
}
