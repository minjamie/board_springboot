package com.example.board.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // Database Table과 맵핑 객체
@Table(name="board") // Database 테이블 이름 user3 와 User라는 객체가 맵핑.
@NoArgsConstructor // 기본생성자가 필요
@Setter
@Getter
public class Board {
    @Id // 이 필드 Table의 PK
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // userId는 자동으로 생성되도록
    private Integer boardId;

    @Column(length = 100)
    private String title;

    @Lob
    private String content;

    @CreationTimestamp
    private LocalDateTime regdate;

    @ManyToOne(fetch = FetchType.LAZY) // 게시물 N --- 1 사용자,  FetchType.EAGER 기본 값으로 무조건 데이터 가져와랴
    @JoinColumn(name = "user_Id")
    private User user;

    // toString 관계 맺는 쿼리는 빼주기
    @Override
    public String toString() {
        return "Board{" +
                "boardId=" + boardId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regdate=" + regdate +
                '}';
    }
}

