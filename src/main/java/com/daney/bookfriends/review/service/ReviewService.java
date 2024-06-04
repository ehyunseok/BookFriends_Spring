package com.daney.bookfriends.review.service;

import com.daney.bookfriends.entity.Review;
import com.daney.bookfriends.review.dto.ReviewDto;
import com.daney.bookfriends.review.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ModelMapper modelMapper;

    //상위 추천 글 5개 불러오기
    public List<ReviewDto> getTop5Reviews() {
        List<Review> reviews = reviewRepository.findTop5ByOrderByLikeCountDesc();
        return reviews.stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }

    // 리뷰 가져오기
    public ReviewDto getReviewById(Integer reviewID) {
        Review review = reviewRepository.findById(reviewID)
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다: " + reviewID));
        return modelMapper.map(review, ReviewDto.class);
    }

    //리뷰 작성
    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = modelMapper.map(reviewDto, Review.class);
        review = reviewRepository.save(review);
        return modelMapper.map(review, ReviewDto.class);
    }

    //리뷰 수정
    public ReviewDto updateReview(ReviewDto reviewDto) {
        Review review = reviewRepository.findById(reviewDto.getReviewID())
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없습니다: " + reviewDto.getReviewID()));
        modelMapper.map(reviewDto, review);
        review = reviewRepository.save(review);
        return modelMapper.map(review, ReviewDto.class);
    }

    
    // 리뷰 삭제
    public void deleteReview(Integer reviewID) {
        reviewRepository.deleteById(reviewID);
    }
}
