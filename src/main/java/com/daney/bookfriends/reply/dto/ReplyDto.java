package com.daney.bookfriends.reply.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ReplyDto /*implements Serializable*/ {
//    private static final long serialVersionUID = 1L;

    private int replyID;
    private String memberID;
    private int postID;
    private int recruitID;
    private String replyContent;
    private int likeCount;
    private Timestamp replyDate;
}
