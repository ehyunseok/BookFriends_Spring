package com.daney.bookfriends.likey.repository;

import com.daney.bookfriends.entity.Likey;
import com.daney.bookfriends.entity.LikeyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeyRepository extends JpaRepository<Likey, LikeyId> {
}
