package com.daney.bookfriends.recruit.service;

import com.daney.bookfriends.member.repository.MemberRepository;
import com.daney.bookfriends.entity.Member;
import com.daney.bookfriends.entity.Recruit;
import com.daney.bookfriends.entity.Reply;
import com.daney.bookfriends.recruit.dto.RecruitDto;
import com.daney.bookfriends.recruit.repository.RecruitRepository;
import com.daney.bookfriends.reply.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecruitServiceTest {

    @Mock
    private RecruitRepository recruitRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private RecruitService recruitService;

    private Recruit recruit;
    private Member member;
    private RecruitDto recruitDto;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setMemberID("testUser");

        recruit = new Recruit();
        recruit.setRecruitID(1);
        recruit.setRecruitTitle("Test Title");
        recruit.setRecruitContent("Test Content");
        recruit.setViewCount(0); // 초기 viewCount 설정
        recruit.setReplies(new ArrayList<>()); // 빈 댓글 리스트 설정
        recruit.setMember(member); // member 설정
        recruit.setRegistDate(new Timestamp(System.currentTimeMillis()));

        recruitDto = new RecruitDto();
        recruitDto.setRecruitID(1);
        recruitDto.setRecruitTitle("Test Title");
        recruitDto.setRecruitContent("Test Content");
        recruitDto.setMemberID("testUser");
    }


    //검색 조건에 따라 필터링된 모집글 리스트 테스트
    @Test
    void getFilteredRecruits() {
        List<Recruit> recruitList = new ArrayList<>();
        recruitList.add(recruit);
        Page<Recruit> page = new PageImpl<>(recruitList);
        Pageable pageable = PageRequest.of(0, 5);

        when(recruitRepository.findByTitleOrContentOrMember(anyString(), any(Pageable.class))).thenReturn(page);

        Page<Recruit> result = recruitService.getFilteredRecruits(1, 5, "전체", "최신순", "Test");

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(recruitRepository, times(1)).findByTitleOrContentOrMember(anyString(), any(Pageable.class));
    }

    //모집글 작성 테스트
    @Test
    void registRecruit() {
        when(memberRepository.findById(anyString())).thenReturn(Optional.of(member));
        when(modelMapper.map(any(RecruitDto.class), eq(Recruit.class))).thenReturn(recruit);
        when(recruitRepository.save(any(Recruit.class))).thenReturn(recruit);

        Recruit result = recruitService.registRecruit(recruitDto, "testUser");

        assertNotNull(result);
        assertEquals("Test Title", result.getRecruitTitle());
        verify(recruitRepository, times(1)).save(any(Recruit.class));
    }

    //모집글 불러오기
    @Test
    void getRecruitID() {
        recruit.setViewCount(0); // 초기 viewCount 설정
        recruit.setReplies(new ArrayList<>()); // 빈 댓글 리스트 설정

        when(recruitRepository.findByIdWithReplies(anyInt())).thenReturn(Optional.of(recruit));
        when(replyRepository.findRepliesByRecruitIdOrderByReplyDateDesc(anyInt())).thenReturn(new ArrayList<>());

        Recruit result = recruitService.getRecruitID(1);

        assertNotNull(result);
        assertEquals(1, result.getRecruitID());
        verify(recruitRepository, times(1)).findByIdWithReplies(anyInt());
        verify(replyRepository, times(1)).findRepliesByRecruitIdOrderByReplyDateDesc(anyInt());
    }

    // 모집글 수정하기
    @Test
    void updateRecruit() {
        when(recruitRepository.findById(anyInt())).thenReturn(Optional.of(recruit));
        when(recruitRepository.save(any(Recruit.class))).thenReturn(recruit);

        Recruit result = recruitService.updateRecruit(1, recruitDto, "testUser");

        assertNotNull(result);
        assertEquals("Test Title", result.getRecruitTitle());
        verify(recruitRepository, times(1)).findById(anyInt());
        verify(recruitRepository, times(1)).save(any(Recruit.class));
    }

    //모집글 삭제하기
    @Test
    void deleteRecruit() {
        doNothing().when(recruitRepository).deleteById(anyInt());

        recruitService.deleteRecruit(1);

        verify(recruitRepository, times(1)).deleteById(anyInt());
    }

    //댓글 쓰기
    @Test
    void addReply() {
        when(memberRepository.findByMemberID(anyString())).thenReturn(member);
        when(recruitRepository.findById(anyInt())).thenReturn(Optional.of(recruit));
        when(replyRepository.save(any(Reply.class))).thenReturn(new Reply());

        Reply result = recruitService.addReply(1, "Test Reply", "testUser");

        assertNotNull(result);
        verify(replyRepository, times(1)).save(any(Reply.class));
    }

    //댓글 수정
    @Test
    void updateReply() {
        Reply reply = new Reply();
        reply.setMember(member);

        when(replyRepository.findById(anyInt())).thenReturn(Optional.of(reply));
        when(replyRepository.save(any(Reply.class))).thenReturn(reply);

        Reply result = recruitService.updateReply(1, "Updated Reply", "testUser");

        assertNotNull(result);
        assertEquals("Updated Reply", result.getReplyContent());
        verify(replyRepository, times(1)).findById(anyInt());
        verify(replyRepository, times(1)).save(any(Reply.class));
    }

    //댓글 삭제
    @Test
    void deleteReply() {
        doNothing().when(replyRepository).deleteById(anyInt());

        recruitService.deleteReply(1);

        verify(replyRepository, times(1)).deleteById(anyInt());
    }
}
