package com.example.board.service;

import com.example.board.dao.UserDao;
import com.example.board.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service // 스프링 관리 Bean - 트랜잭션 단위로 실행될 메서드 선언하고 있는 클래스
@RequiredArgsConstructor
public class UserService {
    private  final UserDao userDao;

    // 스프링 UserService를 Bean으로 생성 시  생성자 이용해 생성,
    // userDao Bean이 있는지 보고 해당 Bean을 주입 - DI 생성자 주입

    @Transactional // 하나의 트랜잭션으로 처리하도록 - Spring Boot 트랜잭션 처리하는 트랜잭션 관리자 (Aop)가짐
    public User addUser(String name, String email, String password){
        // 트랜잭션 시작
        User userCheck = userDao.getUser(email);
        if(userCheck !=null){
            throw new RuntimeException("exist email");
        }

        User user = userDao.addUser(email, name, password);

        userDao.mappingUserRole(user.getUserId());
        return user;
        // 트랜잭션 끝
    }

    @Transactional
    public User getUser(String email){
        return userDao.getUser(email);
    }
}
