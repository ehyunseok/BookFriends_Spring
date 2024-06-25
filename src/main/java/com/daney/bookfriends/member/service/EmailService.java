package com.daney.bookfriends.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    //Redis 적용으로 추가
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 인증 코드를 임시로 저장하는 해시맵
    private static final Map<String, VerificationCode> verificationCodes = new HashMap<>();

    // 인증 코드를 생성하고 이메일로 전송하는 메소드
    public void sendVerificationCode(String memberEmail) {
        // 인증 코드를 생성하는 메소드
        String code = generateCode();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(3); // 3분 제한

        // 생성된 코드를 맵에 저장함
        verificationCodes.put(memberEmail, new VerificationCode(code, expirationTime));

        try {
            // 이메일 메시지를 생성하고 설정함
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(memberEmail);
            helper.setSubject("[독서친구] 이메일 인증 코드입니다.");
            helper.setText("<h2>이메일 인증 코드입니다.</h2>" +
                    "<p>인증 코드</p>" +
                    "<h1><b>" + code +"</b></h1>" +
                    "<p>인증 코드를 입력하고 `인증코드 확인`버튼을 클릭하세요.</p>" +
                    "<p>코드 유효 시간은 3분입니다.</p>", true); // HTML 형식으로 이메일 본문 작성

            // 메일을 전송함
            mailSender.send(mimeMessage);
            log.info("Verification email sent to: {}", memberEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email to: {}", memberEmail, e);
        }
    }

    // 입력한 인증 코드가 저장된 코드와 일치하는지 확인하는 메소드
    public boolean verifyCode(String memberEmail, String code) {
        VerificationCode storedCode = verificationCodes.get(memberEmail);
        // 코드가 일치하고 만료되지 않았는지 확인
        if (storedCode != null && storedCode.getCode().equals(code) && LocalDateTime.now().isBefore(storedCode.getExpirationTime())) {
            verificationCodes.remove(memberEmail); // 인증 후 코드 제거
            return true;
        }
        return false;
    }

    // 4자리 인증 코드를 생성하는 메소드
    private String generateCode() {
        return String.valueOf(new Random().nextInt(9000) + 1000);
    }

    // 인증 코드에 제한 시간을 저장하기 위한 위한 클래스
    @Getter
    public static class VerificationCode { // 접근 제어자를 public으로 변경
        private final String code;
        private final LocalDateTime expirationTime;

        public VerificationCode(String code, LocalDateTime expirationTime) {
            this.code = code;
            this.expirationTime = expirationTime;
        }
    }
}
