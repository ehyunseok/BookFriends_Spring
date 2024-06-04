package com.daney.bookfriends.board.dto;

import lombok.Data;

@Data
public class BoardDto {
    private Long postID;
    private String postCategory;
    private String postTitle;
    private String postContent;
    private int likeCount;
    private String userID;
}