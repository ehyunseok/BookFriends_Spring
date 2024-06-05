package com.daney.bookfriends.user.repository;

import com.daney.bookfriends.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // userID로 이메일 확인 상태 가져오기
    Boolean existsByUserIDAndUserEmailChecked(String userID, Boolean checked);

    User findByUserEmail(String userEmail);
}
