package com.daney.bookfriends.Member;

import com.daney.bookfriends.Member.dto.MemberDto;
import com.daney.bookfriends.Member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class MemberIntegrationTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterAndLogin() {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberID("integrationTestUser");
        memberDto.setMemberPassword("password");
        memberDto.setMemberEmail("test@integration.com");
        memberDto.setMemberEmailChecked(true);

        boolean registerResult = memberService.registerMember(memberDto);
        assertTrue(registerResult);

        boolean loginResult = memberService.login("integrationTestUser", "password");
        assertTrue(loginResult);
    }
}
