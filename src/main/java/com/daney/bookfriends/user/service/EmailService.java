package com.daney.bookfriends.user.service;

import com.daney.bookfriends.jwts.JwtService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
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
    public void sendRegistrationConfirmationEmailHtml(String userEmail) {
        String token = jwtService.generateToken(userEmail);
        String confirmationUrl = "http://localhost:8080/confirm?token=" + token;

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setTo(userEmail);
            helper.setSubject("[독서친구]회원가입 인증 메일입니다.");
            helper.setText("<h1>회원가입 인증</h1>" +
                    "<p>회원가입을 완료하려면 아래 링크를 클릭하세요.</p>" +
                    "<a href='" + confirmationUrl + "'>인증하기</a>", true); // true for HTML
            mailSender.send(mimeMessage);
            log.info("Confirmation email sent to: {}", userEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email to: {}", userEmail, e);
        }
    }
}
