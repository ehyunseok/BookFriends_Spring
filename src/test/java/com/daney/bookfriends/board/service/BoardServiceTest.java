//package com.daney.bookfriends.board.service;
//
//import com.daney.bookfriends.Member.repository.MemberRepository;
//import com.daney.bookfriends.board.dto.BoardDto;
//import com.daney.bookfriends.board.repository.BoardRepository;
//import com.daney.bookfriends.entity.Board;
//import com.daney.bookfriends.entity.Member;
//import com.daney.bookfriends.reply.repository.ReplyRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@DataJpaTest
//@ExtendWith(SpringExtension.class)
//public class BoardServiceTest {
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private ReplyRepository replyRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private BoardService boardService;
//
//    @Test
//    public void testGetFilteredPosts() {
//        Board board = new Board();
//        board.setPostID(1);
//
//        boardRepository.save(board);
//
//        Optional<Board> result = boardRepository.findById(1);
//
//        assertEquals(1, result.get().getPostID());
//    }
//}
