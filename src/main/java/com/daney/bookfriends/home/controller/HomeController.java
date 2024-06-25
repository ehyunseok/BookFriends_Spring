package com.daney.bookfriends.home.controller;

import com.daney.bookfriends.board.service.BoardService;
import com.daney.bookfriends.review.service.ReviewService;
import com.daney.bookfriends.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public String index(HttpSession session, Model model) {
        return "index";
    }


}
