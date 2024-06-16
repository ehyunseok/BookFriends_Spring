package com.daney.bookfriends.board.dto;

import com.daney.bookfriends.reply.dto.ReplyDto;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
public class BoardDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer postID;
    private String postCategory;
    private String postTitle;
    private String postContent;
    private int viewCount;
    private int likeCount;
    private String memberID;
    private Timestamp postDate;
    private List<ReplyDto> replies;
}