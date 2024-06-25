package com.daney.bookfriends.member.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    // 이메일 인증 코드 전송 성공 테스트
    @Test
    void sendVerificationCode_Success() throws MessagingException {
        String memberEmail = "test@example.com";
        MimeMessage mimeMessage = mock(MimeMessage.class);
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailService.sendVerificationCode(memberEmail);

        verify(mailSender, times(1)).send(mimeMessage);
    }

    // 인증 코드 성공 테스트
    @Test
    void verifyCode_Success() {
        String memberEmail = "test@example.com";
        String code = "1234";
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(3);
        EmailService.VerificationCode verificationCode = new EmailService.VerificationCode(code, expirationTime);
        Map<String, EmailService.VerificationCode> verificationCodes = (Map<String, EmailService.VerificationCode>) getPrivateField(emailService, "verificationCodes");
        verificationCodes.put(memberEmail, verificationCode);

        boolean result = emailService.verifyCode(memberEmail, code);

        assertTrue(result);
        assertFalse(verificationCodes.containsKey(memberEmail));  // 코드 제거 확인
    }

    // 인증 코드 실패 테스트
    @Test
    void verifyCode_Failure_IncorrectCode() {
        String memberEmail = "test@example.com";
        String code = "1234";
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(3);
        EmailService.VerificationCode verificationCode = new EmailService.VerificationCode(code, expirationTime);
        Map<String, EmailService.VerificationCode> verificationCodes = (Map<String, EmailService.VerificationCode>) getPrivateField(emailService, "verificationCodes");
        verificationCodes.put(memberEmail, verificationCode);

        boolean result = emailService.verifyCode(memberEmail, "wrongCode");

        assertFalse(result);
        assertTrue(verificationCodes.containsKey(memberEmail));  // 코드가 여전히 있음
    }

    private Object getPrivateField(Object targetObject, String fieldName) {
        try {
            java.lang.reflect.Field field = targetObject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(targetObject);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
