package com.daney.bookfriends.recruit.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RecruitDto {
    private Integer recruitID;
    private String memberID;
    private String recruitStatus;
    private String recruitTitle;
    private String recruitContent;
    private Timestamp registDate;
    private int viewCount;



}
