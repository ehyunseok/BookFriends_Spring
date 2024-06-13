package com.daney.bookfriends.Member.service;

import com.daney.bookfriends.entity.Member;
import com.daney.bookfriends.Member.dto.MemberDto;
import com.daney.bookfriends.Member.repository.MemberRepository;
import com.daney.bookfriends.jwt.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

        log.info("Authentication token: {}", authToken);
        log.info("Authenticated user: {}", SecurityContextHolder.getContext().getAuthentication().getName());
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
        //emailService.sendRegistrationConfirmationEmailHtml(memberEmail);
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

//    // 이메일 확인 여부
//    public boolean isMemberEmailChecked(String memberID) {
//        log.info("Checking email status for member with ID: {}", memberID);
//        MemberDto member = getMemberById(memberID);
//        boolean isChecked = member != null && Boolean.TRUE.equals(member.getMemberEmailChecked());
//        log.info("Email checked status for Member with ID: {} is {}", memberID, isChecked);
//        return isChecked;
//    }

//로그인
    public boolean login(String memberID, String memberPassword) {
        log.info("Attempting to log in with memberID: {}", memberID);

        //ID와 Password가 잘 전달 되었는지 확인
        if(memberID == null || memberID.trim().isEmpty() || memberPassword == null || memberPassword.trim().isEmpty()){
            log.warn("Login attempt failed: memberID or memberPassword is empty");
            return false;
        }

        Member member = memberRepository.findByMemberID(memberID);
        if(member == null){
            log.warn("Login failed: memberID {} not found", memberID);
            return false;   //ID가 존재하지 않음
        }
        if(passwordEncoder.matches(member.getMemberPassword(), memberPassword)){
            log.info("Login successful for memberID: {}", memberID);
            autoLogin(memberID);
            return true;    // 인증 성공
        } else {
            log.warn("Login failed: incorrect password for memberID: {}", memberID);
            return false;   // 비밀번호가 틀림
        }
    }


}
