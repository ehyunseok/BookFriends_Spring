package com.daney.bookfriends.board.repository;

import com.daney.bookfriends.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    // 상위 5개의 인기 게시글을 가져오는 메서드
    List<Board> findTop5ByOrderByLikeCountDesc();

    @Query("SELECT b FROM Board b " +
            "WHERE b.postTitle LIKE %:search% " +
            "OR b.postContent LIKE %:search% " +
            "OR b.member.memberID LIKE %:search%")
    Page<Board> findByTitleOrContentOrMember(@Param("search") String search, Pageable pageable);

    @Query("SELECT b FROM Board b " +
            "WHERE b.postCategory = :postCategory " +
            "AND (b.postTitle LIKE %:search% " +
            "OR b.postContent LIKE %:search% " +
            "OR b.member.memberID " +
            "LIKE %:search%)")
    Page<Board> findByCategoryAndTitleOrContentOrMember(@Param("postCategory") String postCategory, @Param("search") String search, Pageable pageable);
}
