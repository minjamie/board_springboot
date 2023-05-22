package com.example.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@ToString // Ojbect toString 메소드 자동 생성
public class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private LocalDateTime regDate;
}
