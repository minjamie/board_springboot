package com.example.board.controller;

import com.example.board.dto.LoginInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller // HTTP 응답 하는 컴포넌트 - 스프링 부트 자동으로 Bean 생성
public class BoardController {
    @GetMapping("/")
    public String list(HttpSession httpSession, Model model) {//HttpSession, Model Spring 자동 넣어줌
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        model.addAttribute("loginInfo", loginInfo);
        return "list";
    }

    @GetMapping("/board")
    public String board(
            @RequestParam("id") int id
    ) {
        System.out.println(id);
        return "board";
    }
}
