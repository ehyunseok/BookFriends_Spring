package com.daney.bookfriends.user.controller;

import com.daney.bookfriends.user.dto.UserDto;
import com.daney.bookfriends.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/bookfriends/user")
public class UserController {

    @Autowired
    private UserService userService;

    //user 로그인
    @GetMapping("/user/login")
    public String login() {
        return "userLogin";
    }

    //user 가입
    @GetMapping("/user/register")
    public String register() {
        return "userRegister";
    }


    // userID로 user 찾기
    @GetMapping("/{userID}")
    public UserDto getUserById(@PathVariable String userID){
        return userService.getUserById(userID);
    }

    // 유저 가입
    @PostMapping
    public UserDto joinUser(@RequestBody UserDto userDto){
        return userService.joinUser(userDto);
    }

    // 유저 정보 수정
    @PutMapping("/{userID}")
    public UserDto updateUser(@PathVariable String userID, @RequestBody UserDto userDto){
        userDto.setUserID(userID);
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/{userID}")
    public void deleteUser(@PathVariable String userID) {
        userService.deleteUser(userID);
    }

}
