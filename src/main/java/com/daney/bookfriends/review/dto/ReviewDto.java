package com.daney.bookfriends.review.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewDto {
    private Integer reviewID;
    private String userID; // User의 userID를 사용
    private String bookName;
    private String authorName;
    private String publisher;
    private String category;
    private String reviewTitle;
    private String reviewContent;
    private Integer reviewScore;
    private Timestamp registDate;
    private Integer likeCount;
    private Integer viewCount;
}