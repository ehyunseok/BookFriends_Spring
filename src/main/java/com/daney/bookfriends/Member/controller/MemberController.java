package com.daney.bookfriends.Member.controller;

import com.daney.bookfriends.Member.service.EmailService;
import com.daney.bookfriends.jwt.JwtService;
import com.daney.bookfriends.Member.dto.MemberDto;
import com.daney.bookfriends.Member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;


    // 회원가입
    // 회원가입 페이지 경로
    @GetMapping("/join")
    public String join() {
        return "member/join";
    }

    // 아이디 중복 검사
    @GetMapping("/checkMemberID")
    @ResponseBody
    public boolean checkMemberID(@RequestParam("memberID") String memberID) {
        return memberService.isMemberIDDuplicate(memberID);
    }

    // 이메일로 인증 코드 발송 요청 처리
    @PostMapping("/sendVerificationCode")
    @ResponseBody
    public Map<String, String> sendVerificationCode(@RequestParam String memberEmail){
        emailService.sendVerificationCode(memberEmail);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Verification code sent");
        return response;
    }

    // 인증 코드 확인 요청 처리
    @PostMapping("/verifyCode")
    @ResponseBody
    public boolean verifyCode(@RequestParam String memberEmail, @RequestParam String authCode, HttpSession session){
        boolean isValid = emailService.verifyCode(memberEmail, authCode);
        if(isValid){
            session.setAttribute("emailVerified", true);    // 이메일 인증 성공 시 세션에 저장
        } else{
            session.removeAttribute("emailVerified");
        }

        return isValid;
    }


    // 회원가입 요청 처리
    @PostMapping("/join")
    public String registerMember(@ModelAttribute MemberDto memberDto, Model model, HttpSession session){
        Boolean emailVerified = (Boolean) session.getAttribute("emailVerified");
        if(emailVerified != null && emailVerified) {
            if (!memberService.isMemberIDDuplicate(memberDto.getMemberID())) {
                memberDto.setMemberEmailChecked(true);
                boolean registered = memberService.registerMember(memberDto);
                if (registered) {
                    return "member/login"; // 가입이 완료되면 로그인 페이지로 이동
                }
            }
            model.addAttribute("error", "사용할 수 없는 아이디이거나 기타 오류가 발생했습니다.");
        } else {
            model.addAttribute("error","이메일 인증 코드가 올바르지 않거나 만료되었습니다.");
        }
        return "member/join";  // 오류 시 회원가입 페이지 유지
    }


    // 인증메일 다시 보내기
    @GetMapping("/resendConfirmation")
    public String resendConfirmation(Model model){
        model.addAttribute("message", "인증메일이 다시 전송되었습니다.");
        model.addAttribute("redirectUrl", "/"); // 메일 재전송 후 메인 페이지로 이동
        return "confirmationResult"; // 결과 페이지
    }



    // 로그인
    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDto memberDto, Model model){
        log.info("Login attempt for memberID: {}", memberDto.getMemberID());

        // memberID와 memberPassword가 제대로 전달 됐는지 확인하기
        if(memberDto.getMemberID() == null || memberDto.getMemberID().isEmpty()){
            log.warn("Login failed: memberID is empty");
            model.addAttribute("error", "아이디를 입력해주세요.");
            return "member/login";
        }
        if(memberDto.getMemberEmailChecked()==null || memberDto.getMemberPassword().isEmpty()){
            log.warn("Login failed: memberEmail is empty");
            model.addAttribute("error", "비밀번호를 입력해주세요.");
            return "member/login";
        }

        boolean loginSuccessful = memberService.login(memberDto.getMemberID(), memberDto.getMemberPassword());
        if(loginSuccessful){
            log.info("Login successful for memberID: {}", memberDto.getMemberID());
            return "redirect:/";    //로그인 성공 시 메인 페이지로 이동
        } else {
            log.warn("Login failed for memberID: {}", memberDto.getMemberID());
            model.addAttribute("error", "아이디 또는 비밀번호가 틀렸습니다.");
            return "member/login";  // 로그인 실패 시 로그인 페이지로 돌아감
        }
    }


// 로그아웃은 spring security로 처리~~(SecurityConfig)



    //회원 아이디로 회원 찾기
    @GetMapping("/{memberID}")
    @ResponseBody
    public MemberDto getMemberById(@PathVariable String memberID){
        return memberService.getMemberById(memberID);
    }


    //회원정보 수정
    @PutMapping("/{memberID}")
    @ResponseBody
    public MemberDto updateMember(@PathVariable String memberID, @RequestBody MemberDto memberDto){
        memberDto.setMemberID(memberID);
        return memberService.updateMember(memberDto);
    }

    //회원정보 삭제
    @DeleteMapping("/{memberID}")
    @ResponseBody
    public void deleteMember(@PathVariable String memberID) {
        memberService.deleteMember(memberID);
    }
}