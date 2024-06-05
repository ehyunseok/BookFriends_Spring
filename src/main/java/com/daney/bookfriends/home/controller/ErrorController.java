package com.daney.bookfriends.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "redirect:/member/login"; //로그인 페이지로 이동
    }

}
