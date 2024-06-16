package com.daney.bookfriends.reply.repository;

import com.daney.bookfriends.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    //게시판 상세페이지-댓글을 최신순으로 정렬
    @Query("SELECT r FROM Reply r WHERE r.board.postID = :postID ORDER BY r.replyDate DESC")
    List<Reply> findRepliesByBoardIdOrderByReplyDateDesc(@Param("postID") Integer postID);

    //모임모집 상세페이지-댓글을 최신순으로 정렬
    @Query("SELECT r FROM Reply r WHERE r.recruit.recruitID = :recruitID ORDER BY r.replyDate DESC")
    List<Reply> findRepliesByRecruitIdOrderByReplyDateDesc(@Param("recruitID") Integer recruitID);


}
