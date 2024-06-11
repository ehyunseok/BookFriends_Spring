package com.daney.bookfriends.chat.repository;

import com.daney.bookfriends.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    // JPQL 쿼리를 사용하여 memberID가 sender or receiver인 채팅 전부를 조회함
    @Query("SELECT c FROM Chat c " +
            "WHERE c.sender.memberID = :memberID " +
            "OR c.receiver.memberID = :memberID " +
            "ORDER BY c.chatTime DESC")
    List<Chat> findBySenderOrReceiver(String memberID);

    // JPQL 쿼리를 사용하여 senderID와 receiverID를 기반으로 채팅 기록을 과거 메시지부터 현재 메시지 순으로 가져오는 메서드
    @Query("SELECT c FROM Chat c WHERE (c.sender.memberID = :senderID AND c.receiver.memberID = :receiverID) OR (c.sender.memberID = :receiverID AND c.receiver.memberID = :senderID) ORDER BY c.chatTime ASC")
    List<Chat> findBySenderAndReceiver(String senderID, String receiverID);

    @Query("SELECT c FROM Chat c WHERE ((c.sender.memberID = :senderID AND c.receiver.memberID = :receiverID) OR (c.sender.memberID = :receiverID AND c.receiver.memberID = :senderID)) AND c.chatID > :lastID ORDER BY c.chatTime ASC")
    List<Chat> findBySenderAndReceiverAfter(String senderID, String receiverID, Integer lastID);
}
