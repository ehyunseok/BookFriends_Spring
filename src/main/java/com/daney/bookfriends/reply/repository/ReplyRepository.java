package com.daney.bookfriends.reply.repository;

import com.daney.bookfriends.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    //댓글을 최신순으로 정렬함
    @Query("SELECT r FROM Reply r " +
            "WHERE r.board.postID = :postID " +
            "ORDER BY r.replyDate DESC")
    List<Reply> findByPostIDOrderByReplyDate(Integer postID);

    //댓글을 최신순으로 정렬함
    @Query("SELECT r FROM Reply r " +
            "WHERE r.recruit.recruitID = :recruitID " +
            "ORDER BY r.replyDate DESC")
    List<Reply> findByRecruitIDOrderByReplyDate(Integer recruitID);


}
