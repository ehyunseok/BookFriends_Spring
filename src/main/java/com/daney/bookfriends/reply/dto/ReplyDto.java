package com.daney.bookfriends.reply.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReplyDto {

    private int replyID;
    private String memberID;
    private int postID;
    private int recruitID;
    private String replyContent;
    private int likeCount;
    private Timestamp replyDate;
}
