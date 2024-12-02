package com.skboard.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.skboard.dto.userDto;
import com.skboard.service.UserDetailService;
import com.skboard.service.UserService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class MainController {

	private final UserService userService;

    @GetMapping("/")
    public String index(){

        return "indexPage";
    }

    @GetMapping("/login/login")
    public String loginPage(){

        return "login";
    }

    @GetMapping("/login/join")
    public String joinPage(){

        return "login/join";
    }

    @PostMapping("/login/join")
    public String userJoin(@ModelAttribute userDto requestDto) {
        int error = 4/0;
        userService.addUser(requestDto);

        return "login/login";
    }

    @GetMapping("/admins")
    public String adminPage(){

        return "admin/admin";
    }

    @GetMapping("/posts")
    public String posts(@AuthenticationPrincipal UserDetails user, Model model){
        model.addAttribute("user",user.getUsername());
        return "post/post";
    }
}
