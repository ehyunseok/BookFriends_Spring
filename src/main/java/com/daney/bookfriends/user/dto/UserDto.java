package com.daney.bookfriends.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserDto {
    private String userID;
    private String userPassword;
    private String userEmail;
    private Boolean userEmailChecked;
}
