package com.daney.bookfriends.review.controller;

import com.daney.bookfriends.review.dto.ReviewDto;
import com.daney.bookfriends.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getTop5Reviews() {
        List<ReviewDto> reviews = reviewService.getTop5Reviews();
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewID}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Integer reviewID) {
        ReviewDto review = reviewService.getReviewById(reviewID);
        return ResponseEntity.ok(review);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto) {
        ReviewDto createdReview = reviewService.createReview(reviewDto);
        return ResponseEntity.ok(createdReview);
    }

    @PutMapping("/{reviewID}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Integer reviewID, @RequestBody ReviewDto reviewDto) {
        reviewDto.setReviewID(reviewID);
        ReviewDto updatedReview = reviewService.updateReview(reviewDto);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{reviewID}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer reviewID) {
        reviewService.deleteReview(reviewID);
        return ResponseEntity.noContent().build();
    }
}