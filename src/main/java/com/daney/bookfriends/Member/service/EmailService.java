package com.daney.bookfriends.Member.service;

import com.daney.bookfriends.jwts.JwtService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtService jwtService;

    // 회원가입 후 이메일 발송 로직에 추가
    public void sendRegistrationConfirmationEmailHtml(String memberEmail) {
        String token = jwtService.generateToken(memberEmail);
        String confirmationUrl = "http://localhost:8080/member/confirm?token=" + token;

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(memberEmail);
            helper.setSubject("[독서친구]회원가입 인증 메일입니다.");
            helper.setText("<h1>회원가입 인증</h1>" +
                    "<p>회원가입을 완료하려면 아래 링크를 클릭하세요.</p>" +
                    "<a href='" + confirmationUrl + "'>인증하기</a>" +
                    "<p>⚠️유효시간은 전송 시간으로부터 1시간 이내입니다.⚠️</p>", true); // true for HTML
            mailSender.send(mimeMessage);
            log.info("Confirmation email sent to: {}", memberEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email to: {}", memberEmail, e);
        }
    }
}
