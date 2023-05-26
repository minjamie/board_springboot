package com.example.board.repository;

import com.example.board.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    // jpql 사용 가능 - sql과 비슷하지만 아님, 객체지향언어로 from 뒤에 있는 것이 엔티티명
    // Board 엔티티 조회, 엔티티는 결국 테이블과 관계
    @Query(value = "select b from Board b join fetch b.user") //fetch로 n+1 문제 해결
    List<Board> getBoards();

    @Query(value = "select count(b) from Board b")
    Long getBoardCount();

    // 관리자가 쓴 글 목록 구하는 jpql 작성 가능한지?
    @Query(value = "select b from Board b inner join fetch b.user u inner join u.roles r where r.name = :roleName") //fetch 대신 alias로 가져오고자하는 엔티티 alias 컬럼 추가 조회
    List<Board> getBoardsByAdmin(@Param("roleName") String roleName);
}
