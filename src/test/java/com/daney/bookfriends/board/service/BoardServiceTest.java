package com.daney.bookfriends.board.service;

import com.daney.bookfriends.board.dto.BoardDto;
import com.daney.bookfriends.board.repository.BoardRepository;
import com.daney.bookfriends.entity.Board;
import com.daney.bookfriends.entity.Member;
import com.daney.bookfriends.entity.Reply;
import com.daney.bookfriends.reply.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private BoardService boardService;

    private Board board;
    private Member member;
    private BoardDto boardDto;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setMemberID("testUser");

        board = new Board();
        board.setPostID(1);
        board.setPostTitle("Test Title");
        board.setPostContent("Test Content");
        board.setViewCount(0); // 초기 viewCount 설정
        board.setMember(member); // member 설정
        board.setPostDate(new Timestamp(System.currentTimeMillis()));

        boardDto = new BoardDto();
        boardDto.setPostID(1);
        boardDto.setPostTitle("Test Title");
        boardDto.setPostContent("Test Content");
        boardDto.setMemberID("testUser");
    }

    @Test
    void getPostById() {
        when(boardRepository.findById(anyInt())).thenReturn(Optional.of(board));

        Board result = boardService.getPostById(1);

        assertNotNull(result);
        assertEquals(1, result.getPostID());
        assertEquals("Test Title", result.getPostTitle());
        verify(boardRepository, times(1)).findById(anyInt());
    }

    @Test
    void updatePost() {
        when(boardRepository.findById(anyInt())).thenReturn(Optional.of(board));
        when(boardRepository.save(any(Board.class))).thenReturn(board);

        Board result = boardService.updatePost(1, boardDto, "testUser");

        assertNotNull(result);
        assertEquals(1, result.getPostID());
        assertEquals("Test Title", result.getPostTitle());
        verify(boardRepository, times(1)).findById(anyInt());
        verify(boardRepository, times(1)).save(any(Board.class));
    }

    @Test
    void deletePost() {
        doNothing().when(boardRepository).deleteById(anyInt());

        boardService.deletePost(1);

        verify(boardRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void addReply() {
        Reply reply = new Reply();
        reply.setReplyContent("Test Reply");
        reply.setBoard(board);
        reply.setMember(member);

        when(boardRepository.findById(anyInt())).thenReturn(Optional.of(board));
        when(replyRepository.save(any(Reply.class))).thenReturn(reply);

        Reply result = boardService.addReply(1, "Test Reply", "testUser");

        assertNotNull(result);
        assertEquals("Test Reply", result.getReplyContent());
        verify(boardRepository, times(1)).findById(anyInt());
        verify(replyRepository, times(1)).save(any(Reply.class));
    }

    @Test
    void updateReply() {
        Reply reply = new Reply();
        reply.setReplyID(1);
        reply.setReplyContent("Test Reply");
        reply.setMember(member);

        when(replyRepository.findById(anyInt())).thenReturn(Optional.of(reply));
        when(replyRepository.save(any(Reply.class))).thenReturn(reply);

        Reply result = boardService.updateReply(1, "Updated Reply", "testUser");

        assertNotNull(result);
        assertEquals("Updated Reply", result.getReplyContent());
        verify(replyRepository, times(1)).findById(anyInt());
        verify(replyRepository, times(1)).save(any(Reply.class));
    }

    @Test
    void deleteReply() {
        doNothing().when(replyRepository).deleteById(anyInt());

        boardService.deleteReply(1);

        verify(replyRepository, times(1)).deleteById(anyInt());
    }
}
