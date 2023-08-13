package shop.mtcoding.blogv2.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.blogv2.user.UserRequest.loginDTO;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(UserRequest.saveDTO saveDTO) {
        userService.회원가입(saveDTO);
        return "redirect:/loginForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("login")
    public String login(UserRequest.loginDTO loginDTO) {
        userService.로그인(loginDTO);
        return "redirect:/";
    }

}
