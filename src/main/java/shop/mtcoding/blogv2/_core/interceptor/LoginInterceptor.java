package shop.mtcoding.blogv2._core.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blogv2._core.util.ApiUtil;
import shop.mtcoding.blogv2._core.util.Script;
import shop.mtcoding.blogv2.user.User;

// 인터페이스에 default가 있으면 구체적인 메서드 생성 가능(강제성이 없음)
public class LoginInterceptor implements HandlerInterceptor {

    // return이 true이면 컨트롤러 메서드 진입
    // return이 false이면 요청이 종료 됨
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("LoginInterceptor preHandle");

        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");
        String startEndPoint = request.getRequestURI().split("/")[1];

        if (sessionUser == null) {
            if (startEndPoint.equals("api")) {
                response.setHeader("Content-Type", "application/json; charset=utf-8");
                PrintWriter out = response.getWriter();
                ApiUtil<String> apiUtil = new ApiUtil<String>(false, "인증이 필요합니다");
                String responseBody = new ObjectMapper().writeValueAsString(apiUtil);
                out.println(responseBody);
            } else {
                response.setHeader("Content-Type", "text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                out.println(Script.href("/loginForm", "인증이 필요합니다."));
            }
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        System.out.println("LoginInterceptor PostHandle");
    }

}
