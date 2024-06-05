package com.daney.bookfriends.home.controller;

import com.daney.bookfriends.board.dto.BoardDto;
import com.daney.bookfriends.board.service.BoardService;
import com.daney.bookfriends.review.dto.ReviewDto;
import com.daney.bookfriends.review.service.ReviewService;
import com.daney.bookfriends.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/bookfriends")
    public String index(HttpSession session, Model model) {
//        String userID = (String) session.getAttribute("userID");
//        if (userID == null) {
//            return "redirect:/user/login"; // 로그인 페이지로 리다이렉트
//        }
//
//        // 이메일 인증 상태 확인
//        if (!userService.isUserEmailChecked(userID)) {
//            return "redirect:/user/email-confirm"; // 이메일 인증 페이지로 리다이렉트
//        }

//        List<BoardDto> top5Boards = boardService.getTop5Boards();
//        List<ReviewDto> top5Reviews = reviewService.getTop5Reviews();

//        model.addAttribute("userID", userID);
//        model.addAttribute("top5Boards", top5Boards);
//        model.addAttribute("top5Reviews", top5Reviews);

        return "index";
    }
}
