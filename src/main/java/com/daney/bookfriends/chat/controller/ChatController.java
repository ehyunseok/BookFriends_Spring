package com.daney.bookfriends.chat.controller;

import com.daney.bookfriends.Member.dto.MemberDto;
import com.daney.bookfriends.chat.dto.ChatDto;
import com.daney.bookfriends.entity.Member;
import com.daney.bookfriends.chat.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public String getChatList(Model model, Principal principal) {
        String memberID = principal.getName();
        model.addAttribute("memberID", memberID);

        List<ChatDto> chatList = chatService.getChatList(memberID);

        // 중복 제거 로직 추가
        Map<String, ChatDto> chatMap = new LinkedHashMap<>();
        for (ChatDto chat : chatList) {
            chatMap.put(chat.getOtherMemberID(), chat);
        }
        List<ChatDto> uniqueChatList = new ArrayList<>(chatMap.values());

        model.addAttribute("chatList", uniqueChatList);

        String lastMessageTimeAgo = chatList.isEmpty() ? "N/A" : chatList.get(0).getLastMessageTimeAgo();
        model.addAttribute("lastMessageTimeAgo", lastMessageTimeAgo);

        long unreadMessageCount = chatService.getUnreadMessageCount(memberID);
        model.addAttribute("unreadMessageCount", unreadMessageCount);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        model.addAttribute("currentDate", currentDate);

        log.debug("MemberID: {}", memberID);
        log.debug("Unread Message Count: {}", unreadMessageCount);

        return "chat/chat";
    }

    // 실시간 채팅 페이지
    @GetMapping("/chatting/{receiverID}")
    public String chatting(@PathVariable("receiverID") String receiverID, Principal principal, Model model) {
        String memberID = principal.getName();

        // Member 엔티티를 MemberDto로 변환
        List<Member> chatFriends = chatService.getChatFriends(memberID);
        List<MemberDto> chatFriendDtos = chatFriends.stream().map(member -> {
            MemberDto dto = new MemberDto();
            dto.setMemberID(member.getMemberID());
            // 필요한 경우 lastMessageTime을 설정
            dto.setLastMessageTime(new Date()); // 예시로 현재 시간을 사용
            return dto;
        }).collect(Collectors.toList());

        // 채팅 기록을 가져온다.
        // lastID가 null인 이유는 처음 페이지 로드 시 모든 메시지를 가져오기 위해서이다.
        List<ChatDto> chatHistory = chatService.getChatHistory(memberID, receiverID, null);

        // 모델에 데이터 추가
        model.addAttribute("memberID", memberID);
        model.addAttribute("receiverID", receiverID);
        model.addAttribute("chatFriends", chatFriendDtos);
        model.addAttribute("chatHistory", chatHistory);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());
        model.addAttribute("currentDate", currentDate);

        // 마지막 메시지 ID를 모델에 추가
        if (!chatHistory.isEmpty()) {
            // chatHistory가 비어있지 않으면, 마지막 메시지의 ID를 lastID로 설정함
            model.addAttribute("lastID", chatHistory.get(chatHistory.size() - 1).getChatID());
        } else {
            // 비어 있으면 lastID를 0으로 설정한다.
            model.addAttribute("lastID", 0);
        }

        return "chat/chatting";
    }

    // 채팅 내역 가져오기
    @GetMapping("/getChatHistory")
    @ResponseBody
    public List<ChatDto> getChatHistory(@RequestParam("senderID") String senderID,
                                        @RequestParam("receiverID") String receiverID,
                                        @RequestParam("listType") String listType,
                                        @RequestParam(value = "lastID", required = false) Integer lastID) {
        return chatService.getChatHistory(senderID, receiverID, lastID);
    }


    // 메시지 보내기
    @PostMapping("/sendMessage")
    @ResponseBody
    public String sendMessage(@RequestParam("senderID") String senderID,
                              @RequestParam("receiverID") String receiverID,
                              @RequestParam("message") String message) {
        try {
            log.info("Message sending initiated. Sender: {}, Receiver: {}, Message: {}", senderID, receiverID, message);
            chatService.sendMessage(senderID, receiverID, message);
            log.info("Message sent successfully.");
            return "1"; //성공
        } catch (Exception e){
            log.error("메시지 전송 실패", e);
            return "0";    //실패
        }
    }

    // 채팅 목록 가져오기
    @GetMapping("/chatList")
    @ResponseBody
    public List<ChatDto> getChatList(@RequestParam("senderID") String senderID,
                                     @RequestParam("receiverID") String receiverID,
                                     @RequestParam("listType") String listType,
                                     @RequestParam(value = "lastID", required = false) Integer lastID) {
        return chatService.getChatHistory(senderID, receiverID, lastID);
    }

    // 메시지 읽음처리
    @PostMapping("/markAsRead")
    @ResponseBody
    public ResponseEntity<Void> markAsRead(@RequestParam("senderID") String senderID, @RequestParam("receiverID") String receiverID) {
        try {
            chatService.markMessagesAsRead(senderID, receiverID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("메시지 읽음 처리 실패", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
