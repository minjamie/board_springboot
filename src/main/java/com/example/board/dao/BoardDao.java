package com.example.board.dao;

import com.example.board.dto.Board;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private  final SimpleJdbcInsertOperations insertBoard;

    public BoardDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertBoard = new SimpleJdbcInsert(dataSource)
                .withTableName("board")
                .usingGeneratedKeyColumns("board_id"); // 자동으로 증가되는 id 설정
    }

    @Transactional
    public void addBoard(int userId, String title, String content) {
        Board board = new Board();
        board.setUserId(userId);
        board.setTitle(title);
        board.setContent(content);
        board.setRegDate(LocalDateTime.now());
        SqlParameterSource params = new BeanPropertySqlParameterSource(board);
        insertBoard.execute(params);
    }


    @Transactional(readOnly = true)
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) AS totalCount FROM board";
        Integer totalCount = jdbcTemplate.queryForObject(sql, Map.of(), Integer.class);
        return totalCount.intValue();
    }

    @Transactional(readOnly = true)
    public List<Board> getBoards(int page) {
        int start = (page - 1) * 10;
        String sql = "SELECT b.user_id, b.title, b.regdate, b.view_cnt, b.board_id, u.name FROM board b JOIN user u ON b.user_id = u.user_id ORDER BY b.board_id DESC limit :start, 10";
        RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
        List<Board> list = jdbcTemplate.query(sql, Map.of("start", start), rowMapper);
        return list;
    }

    @Transactional(readOnly = true)
    public Board getBoard(int boardId) {
        // 1건 또는 0건
        String sql = "SELECT b.user_id, b.board_id, b.title, b.regdate, b.view_cnt, b.content, u.name FROM board b INNER JOIN user u ON u.user_id = b.user_id WHERE b.board_id = :boardId";
        RowMapper<Board> rowMapper = BeanPropertyRowMapper.newInstance(Board.class);
        Board board = jdbcTemplate.queryForObject(sql, Map.of("boardId", boardId), rowMapper);
        return board;
    }

    @Transactional
    public void updateViewCnt(int boardId) {
        String sql = "UPDATE board SET view_cnt = view_cnt +1 WHERE board_id = :boardId";
        jdbcTemplate.update(sql, Map.of("boardId", boardId));

    }

    @Transactional
    public void deleteBoard(int boardId) {
        String sql = "DELETE FROM board WHERE board_id = :boardId";
        jdbcTemplate.update(sql, Map.of("boardId", boardId));
    }

    public void updateBoard(int boardId, String title, String content) {
        String sql = "UPDATE board set title = :title, content = :content WHERE board_id = :boardId";
        Board board = new Board();
        board.setBoardId(boardId);
        board.setTitle(title);
        board.setContent(content);
        SqlParameterSource params = new BeanPropertySqlParameterSource(board);
        jdbcTemplate.update(sql, params);
    }
}
