package com.daney.bookfriends.review.repository;

import com.daney.bookfriends.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    // 상위 5개의 추천 게시글을 가져오는 메서드
    List<Review> findTop5ByOrderByLikeCountDesc();


    // 서평 리스트
    @Query("SELECT r FROM Review r " +
            "WHERE r.reviewTitle LIKE %:search% " +
            "OR r.reviewContent LIKE %:search% " +
            "OR r.member.memberID LIKE %:search% " +
            "OR r.bookName LIKE %:search% " +
            "OR r.authorName LIKE %:search% " +
            "OR r.publisher LIKE %:search%")
    Page<Review> findByTitleOrContentOrMemberOrBookNameOrAuthorNameOrPublisher(@Param("search") String search, Pageable pageable);

    @Query("SELECT r FROM Review r " +
            "WHERE r.category = :category " +
            "AND (r.reviewTitle LIKE %:search% " +
            "OR r.reviewContent LIKE %:search% " +
            "OR r.member.memberID LIKE %:search% " +
            "OR r.bookName LIKE %:search% " +
            "OR r.authorName LIKE %:search% " +
            "OR r.publisher LIKE %:search%)")
    Page<Review> findByCategoryAndTitleOrContentOrMemberOrBookNameOrAuthorNameOrPublisher(@Param("category") String category, @Param("search") String search, Pageable pageable);

}
