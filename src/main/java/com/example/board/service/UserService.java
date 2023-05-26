package com.example.board.service;

import com.example.board.domain.Role;
import com.example.board.domain.User;
import com.example.board.repository.RoleRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service // 스프링 관리 Bean - 트랜잭션 단위로 실행될 메서드 선언하고 있는 클래스
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // 스프링 UserService를 Bean으로 생성 시  생성자 이용해 생성,
    // userDao Bean이 있는지 보고 해당 Bean을 주입 - DI 생성자 주입

    @Transactional // 하나의 트랜잭션으로 처리하도록 - Spring Boot 트랜잭션 처리하는 트랜잭션 관리자 (Aop)가짐
    public User addUser(String name, String email, String password){
        // 트랜잭션 시작
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            throw new RuntimeException("exist email");
        }

        Role role = roleRepository.findByName("ROLE_USER").get();
        User user = new User();
        user.setPassword(password);
        user.setRoles(Set.of(role));
        user.setName(name);
        user.setEmail(email);
        user = userRepository.save(user);
        return user;
        // 트랜잭션 끝
    }

    @Transactional
    public User getUser(String email){

        return userRepository.findByEmail(email).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<String> getRole(int userId) {
        Set<Role> roles = userRepository.findById(userId).orElseThrow().getRoles();
        List<String> list = new ArrayList<>();
        for (Role role: roles) {
            list.add(role.getName());
        }
        return list;
    }
}
