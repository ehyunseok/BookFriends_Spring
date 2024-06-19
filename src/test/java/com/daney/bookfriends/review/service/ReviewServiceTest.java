package com.daney.bookfriends.review.service;

import com.daney.bookfriends.Member.repository.MemberRepository;
import com.daney.bookfriends.entity.Member;
import com.daney.bookfriends.entity.Review;
import com.daney.bookfriends.review.dto.ReviewDto;
import com.daney.bookfriends.review.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;
    private Member member;
    private ReviewDto reviewDto;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setMemberID("testUser");

        review = new Review();
        review.setReviewID(1);
        review.setReviewTitle("Test Title");
        review.setReviewContent("Test Content");
        review.setBookName("Test Book");
        review.setAuthorName("Test Author");
        review.setPublisher("Test Publisher");
        review.setCategory("Test Category");
        review.setViewCount(0); // 초기 viewCount 설정
        review.setMember(member); // member 설정
        review.setRegistDate(new Timestamp(System.currentTimeMillis()));

        reviewDto = new ReviewDto();
        reviewDto.setReviewID(1);
        reviewDto.setReviewTitle("Test Title");
        reviewDto.setReviewContent("Test Content");
        reviewDto.setBookName("Test Book");
        reviewDto.setAuthorName("Test Author");
        reviewDto.setPublisher("Test Publisher");
        reviewDto.setCategory("Test Category");
        reviewDto.setMemberID("testUser");
    }

    //검색 조건에 따라 필터링된 리뷰 리스트를 테스트
    @Test
    void getFilteredReviews() {
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review);
        Page<Review> page = new PageImpl<>(reviewList);
        Pageable pageable = PageRequest.of(0, 6);

        when(reviewRepository.findByTitleOrContentOrMemberOrBookNameOrAuthorNameOrPublisher(anyString(), any(Pageable.class))).thenReturn(page);

        Page<Review> result = reviewService.getFilteredReviews(1, 6, "전체", "최신순", "Test");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(reviewRepository, times(1)).findByTitleOrContentOrMemberOrBookNameOrAuthorNameOrPublisher(anyString(), any(Pageable.class));
    }

    //리뷰 등록 기능을 테스트
    @Test
    void registReview() {
        when(memberRepository.findById(anyString())).thenReturn(Optional.of(member));
        when(modelMapper.map(any(ReviewDto.class), eq(Review.class))).thenReturn(review);
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review result = reviewService.registReview(reviewDto, "testUser");

        assertNotNull(result);
        assertEquals("Test Title", result.getReviewTitle());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    //리뷰 ID로 리뷰를 조회하는 기능을 테스트
    @Test
    void getReviewById() {
        when(reviewRepository.findById(anyInt())).thenReturn(Optional.of(review));

        Review result = reviewService.getReviewById(1);

        assertNotNull(result);
        assertEquals(1, result.getReviewID());
        verify(reviewRepository, times(1)).findById(anyInt());
    }

    //리뷰 수정 기능을 테스트
    @Test
    void updateReview() {
        when(reviewRepository.findById(anyInt())).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review result = reviewService.updateReview(1, reviewDto, "testUser");

        assertNotNull(result);
        assertEquals("Test Title", result.getReviewTitle());
        verify(reviewRepository, times(1)).findById(anyInt());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    //리뷰 삭제 기능을 테스트
    @Test
    void deleteReview() {
        doNothing().when(reviewRepository).deleteById(anyInt());

        reviewService.deleteReview(1);

        verify(reviewRepository, times(1)).deleteById(anyInt());
    }
}
