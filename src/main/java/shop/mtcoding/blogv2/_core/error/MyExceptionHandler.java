package shop.mtcoding.blogv2._core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blogv2._core.util.Script;

//Exception이 발생하면 여기로 다 모임
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String error(RuntimeException e) {
        return Script.back(e.getMessage());
    }
}
