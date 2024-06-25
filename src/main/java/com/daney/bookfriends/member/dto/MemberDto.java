package com.daney.bookfriends.member.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MemberDto {
    private String memberID;
    private String memberPassword;
    private String memberEmail;
    private Boolean memberEmailChecked;

    private Date lastMessageTime;

}
