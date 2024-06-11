package com.daney.bookfriends.Member.repository;

import com.daney.bookfriends.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Member findByMemberID(String memberID);

    Optional<Member> findByMemberEmail(String memberEmail);

    List<Member> findChatFriendsByMemberID(String memberID);
}
