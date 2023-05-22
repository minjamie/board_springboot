package com.example.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class User {
    private int userId;
    private String name;
    private String email;
    private int password;
    private String regDate;


}
