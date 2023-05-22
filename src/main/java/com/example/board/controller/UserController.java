package com.example.board.controller;

import com.example.board.dto.LoginInfo;
import com.example.board.dto.User;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/userRegForm")
    public String userRegForm() {
        return "userRegForm";
    }

    @PostMapping("/userReg")
    public String userReg(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        userService.addUser(name, email, password);
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession httpSession // Spring 자동으로 session 처리하는 HttpSession 객체 넣어줌
    ){
        try {
            User user = userService.getUser(email);
            System.out.println(user);
            if(user.getPassword().equals(password)){
                System.out.println("same password");
                LoginInfo loginInfo = new LoginInfo(user.getUserId(), user.getEmail(), user.getName());
                httpSession.setAttribute("loginInfo", loginInfo); // 첫 번재 파라미터 key, 두번째 파라미터 값
                System.out.println("save loginInfo in session");

            } else {
                throw new RuntimeException("wrong password");
            }
        } catch (Exception ex){
            return "redirect:/loginForm?error=true";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession){
        httpSession.removeAttribute("loginInfo");
        return "redirect:/";
    }

    @GetMapping("/writeForm")
    public String writeForm(){
        return "writeForm";
    }

    @PostMapping("/write")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ){
        System.out.println(title+content);
        return "redirect:/";
    }
}
