package com.daney.bookfriends.chat.service;

import com.daney.bookfriends.member.repository.MemberRepository;
import com.daney.bookfriends.chat.dto.ChatDto;
import com.daney.bookfriends.chat.repository.ChatRepository;
import com.daney.bookfriends.entity.Chat;
import com.daney.bookfriends.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private ChatService chatService;

    private Chat chat;
    private Member sender;
    private Member receiver;
    private ChatDto chatDto;

    @BeforeEach
    void setUp() {
        sender = new Member();
        sender.setMemberID("sender");

        receiver = new Member();
        receiver.setMemberID("receiver");

        chat = new Chat();
        chat.setChatID(1);
        chat.setSender(sender);
        chat.setReceiver(receiver);
        chat.setMessage("Test Message");
        chat.setChatTime(new Timestamp(System.currentTimeMillis()));
        chat.setChatRead(false);

        chatDto = new ChatDto();
        chatDto.setChatID(1);
        chatDto.setSender(sender);
        chatDto.setReceiver(receiver);
        chatDto.setMessage("Test Message");
        chatDto.setChatTime(new Timestamp(System.currentTimeMillis()));
        chatDto.setChatRead(false);
    }

    // 채팅 리스트 불러오기
    @Test
    void getChatList() {
        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);

        when(chatRepository.findBySenderOrReceiver(anyString())).thenReturn(chatList);
        when(modelMapper.map(any(Chat.class), eq(ChatDto.class))).thenReturn(chatDto);

        List<ChatDto> result = chatService.getChatList("sender");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Message", result.get(0).getMessage());
        verify(chatRepository, times(1)).findBySenderOrReceiver(anyString());
    }

    // 채팅 내역 불러오기
    @Test
    void getChatHistory() {
        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);

        when(chatRepository.findBySenderAndReceiver(anyString(), anyString())).thenReturn(chatList);
        when(modelMapper.map(any(Chat.class), eq(ChatDto.class))).thenReturn(chatDto);

        List<ChatDto> result = chatService.getChatHistory("sender", "receiver", null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Message", result.get(0).getMessage());
        verify(chatRepository, times(1)).findBySenderAndReceiver(anyString(), anyString());
    }

    // 메시지 보내기
    @Test
    void sendMessage() {
        when(memberRepository.findById("sender")).thenReturn(Optional.of(sender));
        when(memberRepository.findById("receiver")).thenReturn(Optional.of(receiver));
        when(chatRepository.save(any(Chat.class))).thenReturn(chat);

        chatService.sendMessage("sender", "receiver", "Test Message");

        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    // 메시지 읽음 표시
    @Test
    void markMessagesAsRead() {
        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);

        when(chatRepository.findBySenderAndReceiver(anyString(), anyString())).thenReturn(chatList);

        chatService.markMessagesAsRead("receiver", "sender");

        assertTrue(chat.getChatRead());
        verify(chatRepository, times(1)).findBySenderAndReceiver(anyString(), anyString());
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    // 읽지 않은 메시지 개수
    @Test
    void getUnreadMessageCount() {
        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);

        when(chatRepository.findBySenderOrReceiver(anyString())).thenReturn(chatList);

        long result = chatService.getUnreadMessageCount("receiver");

        assertEquals(1, result);
        verify(chatRepository, times(1)).findBySenderOrReceiver(anyString());
    }
}
