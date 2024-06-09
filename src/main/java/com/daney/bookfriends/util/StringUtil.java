package com.daney.bookfriends.util;

public class StringUtil {
    public static String stripHtml(String input) {
        if(input == null){
            return null;
        }
        return input.replaceAll("<[^>]*>", "");
    }

}
