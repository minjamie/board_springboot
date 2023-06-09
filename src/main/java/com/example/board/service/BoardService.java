package com.example.board.service;

import com.example.board.domain.Board;
import com.example.board.domain.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    @Transactional
    public void addBoard(int userId, String title, String content) {
        User user = userRepository.findById(userId).orElseThrow();
        Board board = new Board();
        board.setUser(user);
        board.setTitle(title);
        board.setContent(content);
        board.setViewCnt(0);
        board.setRegdate(LocalDateTime.now());
        boardRepository.save(board);
    }

    @Transactional(readOnly = true) //select 시 성능 좋아짐
    public long getTotalCount() {
        return boardRepository.getBoardCount();
    }

    @Transactional(readOnly = true) //select 시 성능 좋아짐
    public List<Board> getBoards(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return boardRepository.findByOrderByRegdateDesc(pageable).getContent();
    }

    @Transactional
    public Board getBoard(int boardId, boolean updateViewCnt) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if(updateViewCnt){
            board.setViewCnt(board.getViewCnt() + 1);
        }
        return board;
    }

    public void deleteBoard(int userId, int boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        if(board.getUser().getUserId() == userId){
            boardRepository.delete(board);
        }
    }

    public void deleteBoard(int boardId) {
        boardRepository.deleteById(boardId);
    }

    @Transactional
    public void updateBoard(int boardId, String title, String content) {
        Board board = boardRepository.findById(boardId).orElseThrow();
        board.setTitle(title);
        board.setContent(content);
    }
}
