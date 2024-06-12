package com.daney.bookfriends.util;

// 독서모임 메인페이지에 내용 표시 때문에 추가
public class StringUtil {
    public static String stripHtml(String input) {
        if(input == null){
            return null;
        }
        return input.replaceAll("<[^>]*>", "");
    }

}
