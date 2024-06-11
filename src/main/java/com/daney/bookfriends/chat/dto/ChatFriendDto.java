package com.daney.bookfriends.chat.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ChatFriendDto {
    private String memberID;
    private Timestamp lastMessageTime;
}
