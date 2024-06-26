package com.daney.bookfriends.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
// chat 페이지 시간 표시 때문에 추가
public class DateTimeUtil {

    public static String formatChatTime(Date chatTime) {
        LocalDateTime dateTime = chatTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();

        if (dateTime.toLocalDate().equals(now.toLocalDate())) {
            return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }
    }
}
