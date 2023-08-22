package shop.mtcoding.blogv2.reply;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blogv2.user.User;

@Controller
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @Autowired
    private HttpSession session;

    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable Integer id, Integer boardId) {
        replyService.댓글삭제(id);
        return "redirect:/board/" + boardId;
    }

    @PostMapping("/reply/save")
    public String save(ReplyRequest.ReplySaveDTO replySaveDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        replyService.댓글등록(replySaveDTO, sessionUser.getId());

        return "redirect:/board/" + replySaveDTO.getBoardId();
    }
}
