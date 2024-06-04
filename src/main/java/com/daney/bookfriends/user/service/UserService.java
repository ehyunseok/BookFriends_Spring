package com.daney.bookfriends.user.service;

import com.daney.bookfriends.entity.User;
import com.daney.bookfriends.exception.UserNotFoundException;
import com.daney.bookfriends.user.dto.UserDto;
import com.daney.bookfriends.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper mM;


    //userID로 user 가져오기
    public UserDto getUserById(String userID) {
        User user = userRepo.findById(userID)
                .orElseThrow(() -> new UserNotFoundException("사용자를 해당 아이디로 찾을 수 없습니다: " + userID));
        return mM.map(user, UserDto.class);
    }

    //user 가입
    public UserDto joinUser(UserDto userDto){
        User user = mM.map(userDto, User.class);
        userRepo.save(user);
        return userDto;
    }

    //user 수정
    public UserDto updateUser(UserDto userDto){
        Optional<User> userOptional = userRepo.findById(userDto.getUserID());
        if(userOptional.isPresent()){
            User user = userOptional.get();
            mM.map(userDto, user);
            userRepo.save(user);
            return userDto;
        } else {
            throw new RuntimeException("사용자를 해당 아이디로 찾을 수 없습니다: " +  userDto.getUserID());
        }
    }

    //user 삭제
    public void deleteUser(String userID) {
        userRepo.deleteById(userID);
    }

    // 이메일 확인 여부
    public boolean isUserEmailChecked(String userId) {
        log.info("Checking email status for user with ID: {}", userId);
        UserDto user = getUserById(userId);
        boolean isChecked = user != null && user.getUserEmailChecked();
        log.info("Email checked status for user with ID: {} is {}", userId, isChecked);
        return isChecked;
    }
}
