package shop.mtcoding.blogv2.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.error.ex.MyException;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

// 1. 데이터 받기 (V)
// 2. 인증체크 (:TODO)
// 3. 유효성 검사 (:TODO)
// 4. 핵심로직 호출(서비스)
// 5. view or data 응답
@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HttpSession session;

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
    public String index(@RequestParam(defaultValue = "0") Integer page, HttpServletRequest request) {
        Page<Board> boardPG = boardService.게시글목록보기(page);
        request.setAttribute("boardPG", boardPG);
        request.setAttribute("prevPage", boardPG.getNumber() - 1);
        request.setAttribute("nextPage", boardPG.getNumber() + 1);
        return "index";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Board board = boardService.상세보기(id);
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("test/board/{id}")
    public @ResponseBody Board tsetDetail(@PathVariable Integer id) {
        Board board = boardRepository.mFindByIdJoinRepliesInUser(id).get();
        return board;
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, Model model) {
        Board boardUpdate = boardService.수정페이지(id);
        model.addAttribute("boardUpdate", boardUpdate);
        return "board/updateForm";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, BoardRequest.UpdateDTO updateDTO) {
        // body, where 데이터, session값 convention 설정 (where, body, session 순)
        boardService.글수정(id, updateDTO);
        return "redirect:/board/{id}";
    }

    @PostMapping("/board/{id}/delete")
    public @ResponseBody String delete(@PathVariable Integer id) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new MyException("인증되지 않았습니다.");
        }

        boardService.글삭제(id);
        return Script.href("/");
    }

}
