package com.daney.bookfriends.home.controller;

import com.daney.bookfriends.board.service.BoardService;
import com.daney.bookfriends.review.service.ReviewService;
import com.daney.bookfriends.Member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/bookfriends")
    public String index(HttpSession session, Model model) {
//        String memberID = (String) session.getAttribute("memberID");
//        if (memberID == null) {
//            return "redirect:/member/login"; // 로그인 페이지로 리다이렉트
//        }
//
//        // 이메일 인증 상태 확인
//        if (!memberService.isMemberEmailChecked(memberID)) {
//            return "redirect:/member/email-confirm"; // 이메일 인증 페이지로 리다이렉트
//        }

//        List<BoardDto> top5Boards = boardService.getTop5Boards();
//        List<ReviewDto> top5Reviews = reviewService.getTop5Reviews();

//        model.addAttribute("memberID", memberID);
//        model.addAttribute("top5Boards", top5Boards);
//        model.addAttribute("top5Reviews", top5Reviews);

        return "index";
    }
}
