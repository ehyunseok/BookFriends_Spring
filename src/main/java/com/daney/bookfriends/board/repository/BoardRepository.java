package com.daney.bookfriends.board.repository;

import com.daney.bookfriends.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 상위 5개의 인기 게시글을 가져오는 메서드
    List<Board> findTop5ByOrderByLikeCountDesc();
}
