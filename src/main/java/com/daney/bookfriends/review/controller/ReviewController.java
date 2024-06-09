package com.daney.bookfriends.review.controller;

import com.daney.bookfriends.entity.ItemType;
import com.daney.bookfriends.entity.Review;
import com.daney.bookfriends.likey.service.LikeyService;
import com.daney.bookfriends.review.dto.ReviewDto;
import com.daney.bookfriends.review.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private LikeyService likeyService;

    //서평 메인
    @GetMapping
    public String getReviewList(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(defaultValue = "6") int size,
                                @RequestParam(defaultValue = "전체") String category,
                                @RequestParam(defaultValue = "최신순") String searchType,
                                @RequestParam(defaultValue = "") String search,
                                Model model){
        Page<Review> reviewList = reviewService.getFilteredReviews(page, size, category, searchType, search);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("currentPage", page);
        model.addAttribute("category", category);
        model.addAttribute("searchType", searchType);
        model.addAttribute("search", search);
        return "review/review";
    }

// 서평 작성
    //서평 작성 페이지
    @GetMapping("/regist")
    public String registReviewForm(Model model){
        model.addAttribute("review", new ReviewDto());
        return "review/regist";
    }
    // 서평 post
    @PostMapping("/regist")
    public String registReview(@ModelAttribute("review") ReviewDto reviewDto, Principal principal){
        if(principal != null){
            String memberID = principal.getName();
            reviewService.registReview(reviewDto, memberID);
        }
        return "redirect:/review";
    }

//서평 상세 페이지
    @GetMapping("/post/{reviewID}")
    public String getReview(@PathVariable("reviewID") Integer reviewID, Model model){
        Review review = reviewService.getReviewById(reviewID);
        if(review == null){
            return "redirect:/review";
        }

        // 현재 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentMemberID = null;
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            currentMemberID = userDetails.getUsername();
        }
        model.addAttribute("currentMemberID", currentMemberID);
        model.addAttribute("review", review);
        return "review/post";
    }

    //서평 추천하기
    @PostMapping("/likeReview/{reviewID}")
    @ResponseBody
    public String likeReview(@PathVariable("reviewID") Integer reviewID, @RequestParam("memberID") String memberID){
        boolean isLiked = likeyService.toggleLike(memberID, ItemType.REVIEW, reviewID);
        return isLiked ? "liked" : "unliked";
    }

    //서평 수정하기 페이지로
    @GetMapping("/update/{reviewID}")
    public String updateReviewForm(@PathVariable("reviewID") Integer reviewID, Model model){
        Review review = reviewService.getReviewById(reviewID);

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewID(review.getReviewID());
        reviewDto.setCategory(review.getCategory());
        reviewDto.setBookName(review.getBookName());
        reviewDto.setAuthorName(review.getAuthorName());
        reviewDto.setPublisher(review.getPublisher());
        reviewDto.setReviewTitle(review.getReviewTitle());
        reviewDto.setReviewContent(review.getReviewContent());
        reviewDto.setReviewScore(review.getReviewScore());
        model.addAttribute("review", reviewDto);
        return "review/update";
    }

    //서평 수정
    @PostMapping("/update/{reviewID}")
    public String updateReview(@PathVariable("reviewID") Integer reviewID,
                               @ModelAttribute("review") ReviewDto reviewDto, Principal principal){
        reviewService.updateReview(reviewID, reviewDto, principal.getName());
        return "redirect:/review/post/" + reviewID;
    }

    // 서평 삭제
    @DeleteMapping("/delete/{reviewID}")
    public String deleteReview(@PathVariable("reviewID") Integer reviewID){
        reviewService.deleteReview(reviewID);
        return "redirect:/review";
    }

}