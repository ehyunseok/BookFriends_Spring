package com.daney.bookfriends.review.repository;

import com.daney.bookfriends.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // 상위 5개의 추천 게시글을 가져오는 메서드
    List<Review> findTop5ByOrderByLikeCountDesc();
}
