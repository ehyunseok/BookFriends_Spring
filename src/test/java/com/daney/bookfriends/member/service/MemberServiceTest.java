package com.daney.bookfriends.member.service;

import com.daney.bookfriends.member.dto.MemberDto;
import com.daney.bookfriends.member.repository.MemberRepository;
import com.daney.bookfriends.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @InjectMocks
    private MemberService memberService;

    // 회원가입 성공 테스트
    @Test
    void registerMember_Success() {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberID("testUser");
        memberDto.setMemberPassword("password");
        memberDto.setMemberEmail("yhdaneys@gmail.com");
        memberDto.setMemberEmailChecked(true);

        when(memberRepository.existsById("testUser")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(modelMapper.map(any(MemberDto.class), eq(Member.class))).thenReturn(new Member());
        when(customUserDetailsService.loadUserByUsername("testUser")).thenReturn(mock(UserDetails.class));

        boolean result = memberService.registerMember(memberDto);

        assertTrue(result);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    // 중복 아이디로 가입 시도 테스트
    @Test
    void registerMember_Failure_DuplicateID() {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberID("testUser");

        when(memberRepository.existsById("testUser")).thenReturn(true);

        boolean result = memberService.registerMember(memberDto);

        assertFalse(result);
        verify(memberRepository, never()).save(any(Member.class));
    }

    // 비밀번호 누락 테스트
    @Test
    void registerMember_Failure_EmptyPassword() {
        MemberDto memberDto = new MemberDto();
        memberDto.setMemberID("testUser");
        memberDto.setMemberPassword(""); // Empty password

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            memberService.registerMember(memberDto);
        });

        assertEquals("Password cannot be empty", exception.getMessage());
        verify(memberRepository, never()).save(any(Member.class));
    }

    // 로그인 테스트
    @Test
    void login_Success() {
        Member member = new Member();
        member.setMemberID("testUser");
        member.setMemberPassword("encodedPassword");

        when(memberRepository.findByMemberID("testUser")).thenReturn(member);
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        boolean result = memberService.login("testUser", "password");

        assertTrue(result);
    }

    // 틀린 비번으로 로그인 테스트
    @Test
    void login_Failure_IncorrectPassword() {
        Member member = new Member();
        member.setMemberID("testUser");
        member.setMemberPassword("encodedPassword");

        when(memberRepository.findByMemberID("testUser")).thenReturn(member);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        boolean result = memberService.login("testUser", "wrongPassword");

        assertFalse(result);
    }

    // 존재하지 않는 사용자로 로그인하기
    @Test
    void login_Failure_NonExistentUser() {
        when(memberRepository.findByMemberID("nonExistentUser")).thenReturn(null);

        boolean result = memberService.login("nonExistentUser", "password");

        assertFalse(result);
    }

}
