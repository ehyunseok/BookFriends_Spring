package com.daney.bookfriends.review.service;

import com.daney.bookfriends.Member.repository.MemberRepository;
import com.daney.bookfriends.entity.Member;
import com.daney.bookfriends.entity.Review;
import com.daney.bookfriends.review.dto.ReviewDto;
import com.daney.bookfriends.review.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MemberRepository memberRepository;

    // 모든 리뷰 리스트
    @Cacheable(value = "reviewList", key = "'page-' + #page + '-size-' + #size + '-category-' + #category + '-searchType-' + #searchType + '-search-' + #search")
    public Page<Review> getFilteredReviews(int page, int size, String category, String searchType, String search) {
        Pageable pageable;

        switch (searchType){
            case "조회수순":
                pageable = PageRequest
                        .of(page -1, size, Sort.by("viewCount").descending());
                break;
            case "추천순":
                pageable = PageRequest
                        .of(page -1, size, Sort.by("likeCount").descending());
                break;
            default:
                pageable = PageRequest
                        .of(page -1, size, Sort.by("registDate").descending());
                break;
        }

        if("전체".equals(category)) {
            return reviewRepository.findByTitleOrContentOrMemberOrBookNameOrAuthorNameOrPublisher(search, pageable);
        } else {
            return reviewRepository.findByCategoryAndTitleOrContentOrMemberOrBookNameOrAuthorNameOrPublisher(category, search, pageable);
        }
    }

    // 리뷰 작성하기
    public Review registReview(ReviewDto reviewDto, String memberID) {
        
        Review review = modelMapper.map(reviewDto, Review.class);

        Member member = memberRepository.findById(memberID)
                .orElseThrow(()-> new IllegalArgumentException("Invalid member ID: " + memberID));
        review.setMember(member);
        return reviewRepository.save(review);
    }

    //리뷰 상세페이지
    @Cacheable(value = "review", key = "#reviewID")
    @Transactional
    public Review getReviewById(Integer reviewID) {
        try {
            Review review = reviewRepository.findById(reviewID)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid review ID: " + reviewID));

            review.setViewCount(review.getViewCount() + 1);

            return review;
        } catch (IllegalArgumentException e) {
            log.error("Error retrieving review: {}", e.getMessage());
            return null;
        }
    }

    //서평 수정
    @Transactional
    public Review updateReview(Integer reviewID, ReviewDto reviewDto, String memberID) {
        Review existingReview = reviewRepository.findById(reviewID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review ID: " + reviewID));

        if (!existingReview.getMember().getMemberID().equals(memberID)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        existingReview.setCategory(reviewDto.getCategory());
        existingReview.setBookName(reviewDto.getBookName());
        existingReview.setAuthorName(reviewDto.getAuthorName());
        existingReview.setPublisher(reviewDto.getPublisher());
        existingReview.setReviewTitle(reviewDto.getReviewTitle());
        existingReview.setReviewContent(reviewDto.getReviewContent());
        existingReview.setReviewScore(reviewDto.getReviewScore());

        return reviewRepository.save(existingReview);
    }


    // 서평 삭제
    @Transactional
    public void deleteReview(Integer reviewID) {
        reviewRepository.deleteById(reviewID);
    }
}
