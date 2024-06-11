package com.daney.bookfriends.chat.dto;

import com.daney.bookfriends.entity.Member;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class ChatDto {
    private Integer chatID;
    private Member sender;
    private Member receiver;
    private String message;
    private Timestamp chatTime;
    private boolean chatRead;
    //마지막 메시지 시간 계산
    private String lastMessageTimeAgo;
    //읽지 않은 메시지
    private long unreadCount;
    // 상대방의 memberID
    private String otherMemberID;
    // 포맷된 시간을 저장할 필드 추가
    private String formattedChatTime;
    // 마지막 메시지 시간 필드 추가
    private Date lastMessageTime;


}
