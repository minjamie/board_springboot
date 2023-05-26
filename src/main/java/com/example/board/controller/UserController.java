package com.example.board.controller;

import com.example.board.domain.User;
import com.example.board.dto.LoginInfo;
import com.example.board.service.BoardService;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BoardService boardService;
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
            if(user.getPassword().equals(password)){
                LoginInfo loginInfo = new LoginInfo(user.getUserId(), user.getEmail(), user.getName());
                List<String> roles = userService.getRole(user.getUserId());
                loginInfo.setRoles(roles);
                httpSession.setAttribute("loginInfo", loginInfo); // 첫 번재 파라미터 key, 두번째 파라미터 값

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
    public String writeForm(HttpSession httpSession, Model model){
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        if(loginInfo == null){
            return "redirect:/loginForm";
        }
        model.addAttribute("loginInfo", loginInfo);
        return "writeForm";
    }

    @PostMapping("/write")
    public String write(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            HttpSession httpSession
    ){
        LoginInfo loginInfo = (LoginInfo) httpSession.getAttribute("loginInfo");
        if(loginInfo == null){
            return "redirect:/loginForm";
        }
        boardService.addBoard(loginInfo.getUserId(), title, content);
        return "redirect:/";
    }
}
