package com.example.board.controller;

import com.example.board.domain.Board;
import com.example.board.dto.LoginInfo;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller // HTTP 응답 하는 컴포넌트 - 스프링 부트 자동으로 Bean 생성
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @GetMapping("/")
    public String list(@RequestParam(name="page", defaultValue ="0") int page, HttpSession httpSession, Model model) {//HttpSession, Model Spring 자동 넣어줌
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo", loginInfo);

        long totalCount = boardService.getTotalCount();
        List<Board> list = boardService.getBoards(page);
        long pageCount = totalCount / 10;
        if(pageCount % 10 > 0){
            pageCount++;
        }
        int currentPage = page;
        model.addAttribute("list", list);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPage", currentPage);
        return "list";
    }

    @GetMapping("/board")
    public String board(
            @RequestParam("boardId") int boardId,
            Model model
    ) {
        Board board =  boardService.getBoard(boardId, true);
        model.addAttribute("board",board);
        return "board";
    }

    @GetMapping("/delete")
    public String delete(
            @RequestParam("boardId") int boardId,
            HttpSession httpSession
    ){
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        if(loginInfo == null){
            return "redirect:/loginForm";
        }
        List<String> roles = loginInfo.getRoles();

        if(roles.contains("ROLE_ADMIN")){
            boardService.deleteBoard(boardId);
        } else {
            boardService.deleteBoard(loginInfo.getUserId(), boardId);
        }
        return "redirect:/";
    }

    @GetMapping("/updateForm")
    public String updateForm(
            @RequestParam("boardId") int boardId,
            Model model,
            HttpSession httpSession
    ){
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        if(loginInfo == null){
            return "redirect:/loginForm";
        }
        Board board = boardService.getBoard(boardId, false);
        model.addAttribute("board", board);
        model.addAttribute("loginInfo", loginInfo);
        return "updateForm";
    }

    @PostMapping("/update")
    public String update(@RequestParam("boardId") int boardId,
                         @RequestParam("title") String title,
                         @RequestParam("content") String content,
                         HttpSession httpSession
    ){
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        if(loginInfo == null){
            return "redirect:/loginForm";
        }
        Board board = boardService.getBoard(boardId, false);
        if(board.getUser().getUserId() != loginInfo.getUserId()){
            return "redirect:/board?boardId="+boardId;
        }
        boardService.updateBoard(boardId, title, content);
        return "redirect:/board?boardId="+boardId;
    }
}
