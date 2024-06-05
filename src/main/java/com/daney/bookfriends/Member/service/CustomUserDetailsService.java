package com.daney.bookfriends.Member.service;

import com.daney.bookfriends.Member.repository.MemberRepository;
import com.daney.bookfriends.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberID) throws UsernameNotFoundException {
        Member member = memberRepository.findById(memberID)
                .orElseThrow(() -> new UsernameNotFoundException("No member found with ID: " + memberID));

        return User.withUsername(member.getMemberID())
                .password(member.getMemberPassword())
                .authorities("USER") // 권한 설정 (필요에 따라 변경)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
