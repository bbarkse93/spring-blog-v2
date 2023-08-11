package shop.mtcoding.blogv2.board;

import lombok.Getter;
import lombok.Setter;

public class BoardRequest {
    @Getter
    @Setter
    public static class saveDTO {
        private String title;
        private String content;
    }
}
