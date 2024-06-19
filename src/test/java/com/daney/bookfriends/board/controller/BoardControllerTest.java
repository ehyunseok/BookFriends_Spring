package com.daney.bookfriends.board.controller;

import com.daney.bookfriends.board.dto.BoardDto;
import com.daney.bookfriends.board.service.BoardService;
import com.daney.bookfriends.entity.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BoardControllerTest {

    @Mock
    private BoardService boardService;

    @InjectMocks
    private BoardController boardController;

    private MockMvc mockMvc;
    private BoardDto boardDto;
    private Board board;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(boardController).build();

        boardDto = new BoardDto();
        boardDto.setPostID(1);
        boardDto.setPostTitle("Test Title");
        boardDto.setPostContent("Test Content");

        board = new Board();
        board.setPostID(1);
        board.setPostTitle("Test Title");
        board.setPostContent("Test Content");
        board.setPostDate(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void getBoardList() throws Exception {
        Page<Board> boardPage = new PageImpl<>(Collections.singletonList(board), PageRequest.of(0, 5), 1);
        when(boardService.getFilteredPosts(anyInt(), anyInt(), anyString(), anyString(), anyString())).thenReturn(boardPage);

        mockMvc.perform(get("/board"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/board"))
                .andExpect(model().attributeExists("boardList"))
                .andExpect(model().attributeExists("currentPage"))
                .andExpect(model().attributeExists("totalPages"))
                .andExpect(model().attributeExists("totalItems"))
                .andExpect(model().attributeExists("size"));

        verify(boardService, times(1)).getFilteredPosts(anyInt(), anyInt(), anyString(), anyString(), anyString());
    }

    @Test
    void registPostForm() throws Exception {
        mockMvc.perform(get("/board/regist"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/regist"));
    }

    @Test
    void registPost() throws Exception {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        mockMvc.perform(post("/board/regist")
                        .principal(principal)
                        .flashAttr("board", boardDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board"));

        verify(boardService, times(1)).registPost(any(BoardDto.class), eq("testUser"));
    }

    @Test
    void getPost() throws Exception {
        when(boardService.getPostById(anyInt())).thenReturn(board);

        mockMvc.perform(get("/board/post/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/post"))
                .andExpect(model().attributeExists("board"))
                .andExpect(model().attributeExists("currentMemberID"));

        verify(boardService, times(1)).getPostById(anyInt());
    }

    @Test
    void updatePostForm() throws Exception {
        when(boardService.getPostById(anyInt())).thenReturn(board);

        mockMvc.perform(get("/board/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/update"))
                .andExpect(model().attributeExists("board"));

        verify(boardService, times(1)).getPostById(anyInt());
    }

    @Test
    void updatePost() throws Exception {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        mockMvc.perform(post("/board/update/1")
                        .principal(principal)
                        .flashAttr("board", boardDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/post/1"));

        verify(boardService, times(1)).updatePost(anyInt(), any(BoardDto.class), eq("testUser"));
    }

    @Test
    void deletePost() throws Exception {
        mockMvc.perform(delete("/board/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board"));

        verify(boardService, times(1)).deletePost(anyInt());
    }

    @Test
    void addReply() throws Exception {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        mockMvc.perform(post("/board/registReply")
                        .param("postID", "1")
                        .param("replyContent", "Test Reply")
                        .principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/post/1"));

        verify(boardService, times(1)).addReply(anyInt(), anyString(), eq("testUser"));
    }

    @Test
    void updateReply() throws Exception {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("testUser");

        mockMvc.perform(post("/board/updateReply")
                        .param("replyID", "1")
                        .param("postID", "1")
                        .param("replyContent", "Updated Reply")
                        .principal(principal))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/post/1"));

        verify(boardService, times(1)).updateReply(anyInt(), anyString(), eq("testUser"));
    }

    @Test
    void deleteReply() throws Exception {
        mockMvc.perform(delete("/board/deleteReply/1/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/board/post/1"));

        verify(boardService, times(1)).deleteReply(anyInt());
    }
}
