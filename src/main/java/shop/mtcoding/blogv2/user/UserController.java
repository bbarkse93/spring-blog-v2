package shop.mtcoding.blogv2.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;

@Controller
public class UserController {

    @Autowired // DI(의존성 주입)
    private UserService userService;

    @Autowired
    private HttpSession session; // 서버측 저장소

    // C - V
    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    // M - V - C
    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        // System.out.println("OriginalFilename: " +
        // joinDTO.getPic().getOriginalFilename());
        // System.out.println("Size: " + joinDTO.getPic().getSize());
        // System.out.println("ContentType: " + joinDTO.getPic().getContentType());

        userService.회원가입(joinDTO); // Service에게 핵심로직 위임
        return "user/loginForm"; // persist 초기화
    }

    @GetMapping("/api/check")
    public @ResponseBody ApiUtil<String> check(@RequestParam String username) {
        userService.중복체크(username);

        return new ApiUtil<String>(true, "사용가능한 유저네임 입니다.");
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public @ResponseBody String login(UserRequest.LoginDTO loginDTO) {
        User sessionUser = userService.로그인(loginDTO);
        session.setAttribute("sessionUser", sessionUser);
        return Script.href("/");
    }

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원정보보기(sessionUser.getId());
        request.setAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("/user/update")
    public @ResponseBody String update(UserRequest.UpdateDTO updateDTO) {
        // 1. 회원수정 (Service)
        // 2. 세션 동기화
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원수정(updateDTO, sessionUser.getId());
        session.setAttribute("sessionUser", user);

        return Script.href("/user/updateForm", "회원정보가 수정되었습니다.");
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

}
