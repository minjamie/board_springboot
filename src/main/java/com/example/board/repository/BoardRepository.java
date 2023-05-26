package com.example.board.repository;

import com.example.board.domain.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    // jpql 사용 가능 - sql과 비슷하지만 아님, 객체지향언어로 from 뒤에 있는 것이 엔티티명
    // Board 엔티티 조회, 엔티티는 결국 테이블과 관계
    @Query(value = "select b from Board b join fetch b.user") //fetch로 n+1 문제 해결
    List<Board> getBoards();


    Page<Board> findByOrderByRegdateDesc(Pageable pageable);

    @Query(value = "select count(b) from Board b")
    Long getBoardCount();

    // 관리자가 쓴 글 목록 구하는 jpql 작성 가능한지?
}
