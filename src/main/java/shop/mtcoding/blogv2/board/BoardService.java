package shop.mtcoding.blogv2.board;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import shop.mtcoding.blogv2.user.User;

// Service의 책임
// 핵심로직 처리, 트랜잭션 관리, 예외 처리
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(BoardRequest.SaveDTO saveDTO, int sessionUserId) {
        Board board = Board.builder()
                .title(saveDTO.getTitle())
                .content(saveDTO.getContent())
                .user(User.builder().id(sessionUserId).build())
                .build();
        boardRepository.save(board);
    }

    public Page<Board> 게시글목록보기(Integer page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.Direction.DESC, "id");
        return boardRepository.findAll(pageable);
    }

    public Board 상세보기(Integer id) {
        Optional<Board> boardOP = boardRepository.mFindByIdJoinRepliesInUser(id);
        if (boardOP.isPresent()) {
            return boardOP.get();
        } else {
            throw new RuntimeException(id + "번을 찾을 수 없습니다.");
        }
    }

    public Board 수정페이지(Integer id) {
        return boardRepository.findById(id).get();
    }

    @Transactional
    public void 글수정(Integer id, BoardRequest.UpdateDTO updateDTO) {
        Optional<Board> boardOP = boardRepository.findById(id);
        if (boardOP.isPresent()) {
            Board board = boardOP.get();
            board.setTitle(updateDTO.getTitle());
            board.setContent(updateDTO.getContent());
        } else {
            new RuntimeException(id + "번을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void 글삭제(Integer id) {
        try {
            boardRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(id + "번은 없어요");
        }
    }

}