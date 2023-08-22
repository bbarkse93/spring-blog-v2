package shop.mtcoding.blogv2._core.util;

import lombok.Getter;
import lombok.Setter;

// 공통 응답의 DTO
@Getter
@Setter
// 응답의 타입이 확실하지 않아서 제네릭으로 응답
public class ApiUtil<T> {
    private boolean sucuess; // true
    private T data; // 댓글쓰기 성공

    public ApiUtil(boolean sucuess, T data) {
        this.sucuess = sucuess;
        this.data = data;
    }
}
