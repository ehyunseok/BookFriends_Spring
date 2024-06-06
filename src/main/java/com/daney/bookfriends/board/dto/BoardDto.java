package com.daney.bookfriends.board.dto;

import lombok.Data;

@Data
public class BoardDto {
    private Integer postID;
    private String postCategory;
    private String postTitle;
    private String postContent;
    private int viewCount;
    private int likeCount;
    private String memberID;
}