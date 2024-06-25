package com.daney.bookfriends.member.service;

import com.daney.bookfriends.entity.Member;
import com.daney.bookfriends.member.dto.MemberDto;
import com.daney.bookfriends.member.repository.MemberRepository;
import com.daney.bookfriends.jwt.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtService jwtService;

    //Redis 추가!
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private static final String SESSION_KEY_PREFIX = "session";
    private static final String AUTH_KEY_PREFIX = "auth";

    //member 가입
    // 아이디 중복 확인
    public boolean isMemberIDDuplicate(String memberID){    // 사용자가 입력한 아이디
        boolean isDuplicate = memberRepository.existsById(memberID);
        log.info("member ID '{}' duplication check: {}", memberID, isDuplicate);
        return isDuplicate; // 중복 되면 true, 아니면 false
    }

    //회원 가입 처리 & 회원 정보 저장
    @Transactional
    public boolean registerMember(MemberDto memberDto){
        try {
            // 비밀번호 필드가 비어있는지 확인
            if(memberDto.getMemberPassword() == null || memberDto.getMemberPassword().trim().isEmpty()){
                log.warn("Registration attempt failed: MemberPassword is empty");
                throw new IllegalArgumentException("Password cannot be empty");
            }

            // 아이디 중복 검사
            if(memberRepository.existsById(memberDto.getMemberID())){
                log.warn("Registration attempt failed: MemberID {} already exists", memberDto.getMemberID());
                return false;
            }

            // 비밀번호 암호화
            memberDto.setMemberPassword(passwordEncoder.encode(memberDto.getMemberPassword()));

            // 사용자 정보 저장
            Member member = modelMapper.map(memberDto, Member.class);
            member.setMemberEmailChecked(true);
            memberRepository.save(member);
            log.info("New member registered: {}", memberDto.getMemberID());

            // 자동 로그인 처리
            autoLogin(memberDto.getMemberID());

            return true;
        } catch (Exception e) {
            log.error("Failed to register member: {}", e.getMessage(), e);
            return false;
        }
    }

    // 자동 로그인 처리 로직
    private void autoLogin(String memberID){
        log.info("Auto login for user: {}", memberID);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberID);


        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        // Redis 적용으로 추가
        cacheUserSession(memberID, authToken);

        log.info("Authentication token: {}", authToken);
        log.info("Authenticated user: {}", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    // Redis 적용으로 추가
    private void cacheUserSession(String memberID, UsernamePasswordAuthenticationToken authToken) { // 추가된 메소드
        String sessionKey = SESSION_KEY_PREFIX + memberID;
        redisTemplate.opsForValue().set(sessionKey, authToken, 30, TimeUnit.MINUTES);
    }


    // 이메일 인증 처리
    @Transactional
    public boolean verifyMemberEmail(String memberEmail){
        Optional<Member> optionalMember = memberRepository.findByMemberEmail(memberEmail);
        if(optionalMember.isPresent()){
            Member member = optionalMember.get();
            if(!member.getMemberEmailChecked()){
                member.setMemberEmailChecked(true);
                memberRepository.save(member);
                return true;
            }
        }
        return false;
    }

    //이메일 재전송 로직
    public void resendConfirmationEmail(String memberEmail) {
        String token = jwtService.generateToken(memberEmail);
    }

//member 수정
    public MemberDto updateMember(MemberDto memberDto){
        Optional<Member> memberOptional = memberRepository.findById(memberDto.getMemberID());
        if(memberOptional.isPresent()){
            Member member = memberOptional.get();
            modelMapper.map(memberDto, member);
            memberRepository.save(member);
            return memberDto;
        } else {
            throw new RuntimeException("사용자를 해당 아이디로 찾을 수 없습니다: " +  memberDto.getMemberID());
        }
    }

//member 삭제
    public void deleteMember(String memberID) {
        memberRepository.deleteById(memberID);
    }


// 이메일 인증 여부 확인
    // memberID로 member 가져오기
    public MemberDto getMemberById(String memberID) {
        return memberRepository.findById(memberID)
                .map(member -> modelMapper.map(member, MemberDto.class))
                .orElse(null); // 사용자가 없다면 null 반환
    }


//로그인
    public boolean login(String memberID, String memberPassword, Model model) {
        log.info("Attempting to log in with memberID: {}", memberID);

    // Redis 적용으로 추가 및 변경 start
        // 레디스에서 사용할 인증키를 만듦
        String authKey = AUTH_KEY_PREFIX + memberID;
        // 레디스에서 키를 확인
        if (redisTemplate.hasKey(authKey)) {
            // 인증정보 가져오기: 레디스에서 authToken을 가져온다.
            UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) redisTemplate.opsForValue().get(authKey);
            // authToken이 null이 아니면 SecurityContextHolder에 인증정보를 설정한다.
            if (authToken != null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
                // 인증 정보가 유효하면 true를 반환함
                return true;
            }
        }

        // 사용자 정보 검증
        // memberID를 이용하여  memberRepository에서 사용자 정보를 조회함
        Member member = memberRepository.findByMemberID(memberID);
        // 사용자가 입력한 비밀번호가 db 상에 저장된 비번과 일치하는지 확인
        if (member != null && passwordEncoder.matches(memberPassword, member.getMemberPassword())) {
            // autoLogin 메소드를 호출하여 로그인 처리를 수행함
            autoLogin(memberID);
            // cacheUserAuth 메소드를 호출해서 인증 정보를 레디스에 캐싱함
            cacheUserAuth(memberID, SecurityContextHolder.getContext().getAuthentication()); // 추가된 부분
            // 사용자 정보가 유효하면 true를 반환함
            return true;
        } else {
            if (member == null) {
                log.warn("Login failed: Member with ID {} not found.", memberID);
                model.addAttribute("error", "not_found");
            } else if (!passwordEncoder.matches(memberPassword, member.getMemberPassword())) {
                log.warn("Login failed: Incorrect password for memberID {}.", memberID);
                model.addAttribute("error", "incorrect_password");
            }
            // 사용자 정보가 유효하지 않으면 false를 반환함
            return false;
        }
    //end
    }

    // Redis 적용으로 추가함
    private void cacheUserAuth(String memberID, Authentication authToken) { // 변경된 메소드
        String authKey = AUTH_KEY_PREFIX + memberID;
        redisTemplate.opsForValue().set(authKey, authToken, 60, TimeUnit.MINUTES);
    }

}
