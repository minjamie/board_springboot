package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor // 모든 생성자 필드 초기화하는 애노테이션
public class LoginInfo {
    private int userId;
    private String email;
    private String name;
}
