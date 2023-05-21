package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // HTTP 응답 하는 컴포넌트 - 스프링 부트 자동으로 Bean 생성
public class BoardController {
    @GetMapping("/")
    public String list() {
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
