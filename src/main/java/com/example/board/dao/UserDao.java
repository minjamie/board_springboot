package com.example.board.dao;

import com.example.board.dto.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository // 스프링 관리하는 Beans
public class UserDao {
    // Spring JDBC를 이용한 코드

    @Transactional
    public User addUser(String email, String name, String password){
        // Service에서 이미 트랜잭션 시작했기에 그 트랜재션에 포함
        return null;
    }


    @Transactional
    public void  mappingUserRole(int userId){

    }
}
