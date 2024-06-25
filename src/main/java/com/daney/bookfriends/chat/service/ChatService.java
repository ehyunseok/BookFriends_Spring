package com.daney.bookfriends.chat.service;

import com.daney.bookfriends.member.repository.MemberRepository;
import com.daney.bookfriends.chat.dto.ChatDto;
import com.daney.bookfriends.chat.dto.ChatFriendDto;
import com.daney.bookfriends.chat.repository.ChatRepository;
import com.daney.bookfriends.entity.Chat;
import com.daney.bookfriends.entity.Member;
import com.daney.bookfriends.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import static com.daney.bookfriends.util.DateTimeUtil.formatChatTime;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MemberRepository memberRepository;

    //Redis 추가
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 사용자가 참여한 모든 채팅 리스트 가져오기
    public List<ChatDto> getChatList(String memberID) {
        List<Chat> chats = chatRepository.findBySenderOrReceiver(memberID);
        chats.forEach(chat -> log.debug("Chat time: {}", chat.getChatTime()));

        LocalDateTime latestMessageTime = getLatestMessageTime(chats);
        List<ChatDto> chatDtos = chats.stream().map(chat -> convertToDto(chat, memberID)).collect(Collectors.toList());

        for (ChatDto chatDto : chatDtos) {
            chatDto.setUnreadCount(getUnreadMessageCountForChat(chatDto.getChatID(), memberID));
            chatDto.setFormattedChatTime(formatChatTime(chatDto.getChatTime()));
        }

        // 최신 메시지 시간 기준으로 정렬
        chatDtos.sort(Comparator.comparing(ChatDto::getLastMessageTime));

        chatDtos.forEach(chatDto -> chatDto.setLastMessageTimeAgo(calculateTimeAgo(chatDto.getLastMessageTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())));

        log.debug("Sorted chat list: {}", chatDtos);

        return chatDtos;
    }

    //사용자와 채팅한 사람들 목록
    public List<Member> getChatFriends(String memberID) {
        // ChatRepository에서 채팅한 유저들 찾기
        List<Chat> chats = chatRepository.findBySenderOrReceiver(memberID);
        return chats.stream()
                .flatMap(chat -> List.of(chat.getSender(), chat.getReceiver()).stream())
                .distinct()
                .filter(member -> !member.getMemberID().equals(memberID))
                .collect(Collectors.toList());
    }

    // 사용자의 채팅 히스토리
    @Cacheable(value = "chatHistory", key = "#memberID + '-' + #receiverID + '-' + #lastID") // Redis 캐시 적용
    public List<ChatDto> getChatHistory(String memberID, String receiverID, Integer lastID) {
        List<Chat> chats;
        if (lastID == null) {
            chats = chatRepository.findBySenderAndReceiver(memberID, receiverID);
        } else {
            chats = chatRepository.findBySenderAndReceiverAfter(memberID, receiverID, lastID);
        }
        // 메시지를 과거 메시지부터 현재 메시지 순으로 정렬 (ASC)
        chats.sort(Comparator.comparing(Chat::getChatTime));
        return chats.stream().map(chat -> convertToDto(chat, memberID)).collect(Collectors.toList());
    }

    // 시간 계산1
    private LocalDateTime getLatestMessageTime(List<Chat> chats) {
        return chats.stream()
                .map(chat -> chat.getChatTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.now());
    }

    //시간 계산 2
    private String calculateTimeAgo(LocalDateTime chatTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(chatTime, now);

        if (duration.toDays() > 0) {
            return duration.toDays() + "일 전";
        } else if (duration.toHours() > 0) {
            return duration.toHours() + "시간 전";
        } else {
            return duration.toMinutes() + "분 전";
        }
    }

    // 안 읽은 메시지 개수
    public long getUnreadMessageCount(String memberID) {
        List<Chat> chats = chatRepository.findBySenderOrReceiver(memberID);

        long count = chats.stream()
                .filter(chat -> !chat.getChatRead() && chat.getReceiver().getMemberID().equals(memberID))
                .count();

        log.debug("Unread message count for memberID {}: {}", memberID, count);

        return count;
    }

    // 채팅별 안 읽은 메시지 개수
    private long getUnreadMessageCountForChat(Integer chatID, String memberID) {
        return chatRepository.findById(chatID)
                .filter(chat -> !chat.getChatRead() && !chat.getSender().getMemberID().equals(memberID))
                .map(chat -> 1L)
                .orElse(0L);
    }

    // 채팅한 사용자들의 리스트와 마지막 메시지 시간 가져오기
    public List<ChatFriendDto> getChatFriendsWithLastMessageTime(String memberID) {
        // 해당 사용자의 채팅 목록 조회
        List<Chat> chats = chatRepository.findBySenderOrReceiver(memberID);
        Map<String, ChatFriendDto> friendsMap = new HashMap<>();

        for (Chat chat : chats) {
            // 친구의 ID를 가져옴
            String friendID = chat.getSender().getMemberID().equals(memberID) ? chat.getReceiver().getMemberID() : chat.getSender().getMemberID();
            ChatFriendDto friend = friendsMap.getOrDefault(friendID, new ChatFriendDto());
            friend.setMemberID(friendID);

            // 마지막 메시지 시간을 업데이트
            if (friend.getLastMessageTime() == null || chat.getChatTime().after(friend.getLastMessageTime())) {
                friend.setLastMessageTime(chat.getChatTime());
            }

            friendsMap.put(friendID, friend);
        }

        return new ArrayList<>(friendsMap.values());
    }

    //메시지 전송
    @CacheEvict(value = "chatHistory", key = "#senderID + '-' + #receiverID") // 메시지 전송 시 캐시 무효화
    public void sendMessage(String senderID, String receiverID, String message) {
        Optional<Member> senderOptional = memberRepository.findById(senderID);
        Optional<Member> receiverOptional = memberRepository.findById(receiverID);

        if(!senderOptional.isPresent() || !receiverOptional.isPresent()) {
            throw new IllegalArgumentException("Sender or Receiver member not found");
        }
        Member sender = senderOptional.get();
        Member receiver = receiverOptional.get();

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setReceiver(receiver);
        chat.setMessage(message);
        chat.setChatTime(new Timestamp(System.currentTimeMillis()));
        chat.setChatRead(false);

        chatRepository.save(chat);
    }

    //메시지 읽음 처리
    @CacheEvict(value = "chatHistory", key = "#senderID + '-' + #receiverID") // 메시지 읽음 처리 시 캐시 무효화
    public void markMessagesAsRead(String senderID, String receiverID) {
        List<Chat> chats = chatRepository.findBySenderAndReceiver(senderID, receiverID);
        for (Chat chat : chats) {
            if (!chat.getChatRead() && chat.getReceiver().getMemberID().equals(senderID)) {
                chat.setChatRead(true);
                chatRepository.save(chat);
            }
        }
    }

    //Dto로 변경
    private ChatDto convertToDto(Chat chat, String currentMemberID) {
        ChatDto chatDto = modelMapper.map(chat, ChatDto.class);
        String otherMemberID = chat.getSender().getMemberID().equals(currentMemberID) ? chat.getReceiver().getMemberID() : chat.getSender().getMemberID();
        chatDto.setOtherMemberID(otherMemberID);
        chatDto.setLastMessageTime(chat.getChatTime());
        chatDto.setFormattedChatTime(DateTimeUtil.formatChatTime(chat.getChatTime()));
        return chatDto;
    }
}
