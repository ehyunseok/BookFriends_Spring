package com.daney.bookfriends.Member.dto;

import com.daney.bookfriends.entity.Member;
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
