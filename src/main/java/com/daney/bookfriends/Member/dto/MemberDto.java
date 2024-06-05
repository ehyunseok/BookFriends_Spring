package com.daney.bookfriends.Member.dto;

import lombok.Data;

@Data
public class MemberDto {
    private String memberID;
    private String memberPassword;
    private String memberEmail;
    private Boolean memberEmailChecked;
}
