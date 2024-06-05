package com.daney.bookfriends.user.service;

import com.daney.bookfriends.entity.User;
import com.daney.bookfriends.exception.UserNotFoundException;
import com.daney.bookfriends.user.dto.UserDto;
import com.daney.bookfriends.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailService emailService;

//user 가입
    // 아이디 중복 확인
    public boolean isUserIDDuplicate(String userID){    // 사용자가 입력한 아이디
        boolean isDuplicate = userRepository.existsById(userID);
        log.info("User ID '{}' duplication check: {}", userID, isDuplicate);
        return isDuplicate; // 중복 되면 true, 아니면 false
    }

    //회원 가입 처리 & 회원 정보 저장
    @Transactional
    public boolean registerUser(UserDto userDto){
        // 비밀번호 필드가 비어있는지 확인
        if(userDto.getUserPassword() == null || userDto.getUserPassword().trim().isEmpty()){
            log.warn("Registration attempt failed: UserPassword is empty");
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // 아이디 중복 검사
        if(userRepository.existsById(userDto.getUserID())){
            log.warn("Registration attempt failed: UserID {} already exists", userDto.getUserID());
            return false;
        }

        // 사용자 정보 저장
        User user = modelMapper.map(userDto, User.class);
        user.setUserEmailChecked(false); // 이메일 인증 상태 기본값은 false
        userRepository.save(user);
        log.info("New user registered: {}", userDto.getUserID());
        emailService.sendRegistrationConfirmationEmailHtml(user.getUserEmail());   // 인증 이메일 전송
        return true;
    }



    // 이메일 인증 처리
    @Transactional
    public boolean verifyUserEmail(String userEmail){
        User user = userRepository.findByUserEmail(userEmail);
        if(user != null && !user.getUserEmailChecked()){
            user.setUserEmailChecked(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }



//user 수정
    public UserDto updateUser(UserDto userDto){
        Optional<User> userOptional = userRepository.findById(userDto.getUserID());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            modelMapper.map(userDto, user);
            userRepository.save(user);
            return userDto;
        } else {
            throw new RuntimeException("사용자를 해당 아이디로 찾을 수 없습니다: " +  userDto.getUserID());
        }
    }

//user 삭제
    public void deleteUser(String userID) {
        userRepository.deleteById(userID);
    }


// 이메일 인증 여부 확인
    // userID로 user 가져오기
    public UserDto getUserById(String userID) {
        return userRepository.findById(userID)
                .map(user -> modelMapper.map(user, UserDto.class))
                .orElse(null); // 사용자가 없다면 null 반환
    }

    // 이메일 확인 여부
    public boolean isUserEmailChecked(String userID) {
        log.info("Checking email status for user with ID: {}", userID);
        UserDto user = getUserById(userID);
        boolean isChecked = user != null && Boolean.TRUE.equals(user.getUserEmailChecked());
        log.info("Email checked status for user with ID: {} is {}", userID, isChecked);
        return isChecked;
    }


}
