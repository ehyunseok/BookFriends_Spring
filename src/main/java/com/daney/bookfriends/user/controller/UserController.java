package com.daney.bookfriends.user.controller;

import com.daney.bookfriends.jwts.JwtService;
import com.daney.bookfriends.user.dto.UserDto;
import com.daney.bookfriends.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;



// 회원가입
    // 회원가입 페이지 경로
    @GetMapping("/join")
    public String join() {
    return "user/join";
}

    // 아이디 중복 검사
    @GetMapping("/checkUserID")
    @ResponseBody
    public boolean checkUserID(@RequestParam("userID") String userID) {
        return userService.isUserIDDuplicate(userID);
    }

    // 회원가입 요청 처리
    @PostMapping("/join")
    public String registerUser(@ModelAttribute UserDto userDto, Model model){
        if(!userService.isUserIDDuplicate(userDto.getUserID())){
            boolean registered = userService.registerUser(userDto);
            if(registered){
                return "redirect:/user/login"; // 가입이 완료되면 로그인 페이지로 이동
            }
        }
        model.addAttribute("error", "사용할 수 없는 아이디이거나 기타 오류가 발생했습니다.");
        return "user/join";  // 오류 시 회원가입 페이지 유지
    }

    //이메일 인증 링크 처리
    @GetMapping("/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token){
        if(jwtService.validateToken(token)){
            String userEmail = jwtService.extractEmail(token);
            // 사용자 이메일 인증 처리 로직
            boolean verified = userService.verifyUserEmail(userEmail);
            if(verified){
                return ResponseEntity.ok("이메일 인증 성공");
            } else {
                return ResponseEntity.badRequest().body("이메일 인증 실패");
            }
        }
        return ResponseEntity.badRequest().body("토큰이 유효하지 않거나 만료되었습니다.");
    }


// 로그인
    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/{userID}")
    @ResponseBody
    public UserDto getUserById(@PathVariable String userID){
        return userService.getUserById(userID);
    }


    @PutMapping("/{userID}")
    @ResponseBody
    public UserDto updateUser(@PathVariable String userID, @RequestBody UserDto userDto){
        userDto.setUserID(userID);
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/{userID}")
    @ResponseBody
    public void deleteUser(@PathVariable String userID) {
        userService.deleteUser(userID);
    }
}
