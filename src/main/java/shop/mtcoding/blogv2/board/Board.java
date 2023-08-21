package shop.mtcoding.blogv2.board;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.user.User;

@NoArgsConstructor
@Setter
@Getter
@Table(name = "board_tb")
@Entity // ddl-auto가 create
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;
    @Column(nullable = true, length = 10000)
    private String content;

    // FetchType.LAZY를 붙이면 user객체를 조회하지 않음
    // FetchType.EAGER을 붙이면 user객체를 조회 (default)
    // ObjectMapper 사용할 때 EAGER로 변경해서 테스트
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // fetch는 프로잭션 하는 방식을 제공
    private User user; // 1+N

    // ManyToOne은 기본전략이 EAGER전략
    // OneToMany는 기본전략이 LAZY전략
    @JsonIgnoreProperties({ "board" }) // JsonIgnore에서 불필요한 필드만 무시하는 거
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY) // 얘는 포린키가 아니에여
    private List<Reply> replies = new ArrayList<>();

    @CreationTimestamp // 인서트 될 때 자동으로 시간을 입력
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }
}