package com.daney.bookfriends.recruit.service;

import com.daney.bookfriends.Member.repository.MemberRepository;
import com.daney.bookfriends.entity.Board;
import com.daney.bookfriends.entity.Member;
import com.daney.bookfriends.entity.Recruit;
import com.daney.bookfriends.entity.Reply;
import com.daney.bookfriends.recruit.dto.RecruitDto;
import com.daney.bookfriends.recruit.repository.RecruitRepository;
import com.daney.bookfriends.reply.repository.ReplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RecruitService {

    @Autowired
    private RecruitRepository recruitRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;

    // 모집 메인 리스트
    //@Cacheable(value = "recruits", key = "#page + '-' + #size + '-' + #recruitStatus + '-' + #searchType + '-' + #search")
    public Page<Recruit> getFilteredRecruits(int page, int size, String recruitStatus, String searchType, String search) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("registDate").descending());
        if ("조회수순".equals(searchType)) {
            pageable = PageRequest.of(page - 1, size, Sort.by("viewCount").descending());
        }
        if ("전체".equals(recruitStatus)) {
            return recruitRepository.findByTitleOrContentOrMember("%" + search + "%", pageable);
        } else {
            return recruitRepository.findByRecruitStatusAndTitleOrContentOrMember(recruitStatus, "%" + search + "%", pageable);
        }
    }


    // 모집글 작성하기
    public Recruit registRecruit(RecruitDto recruitDto, String memberID) {
        Recruit recruit = modelMapper.map(recruitDto, Recruit.class);
        Member member = memberRepository.findById(memberID).orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberID));
        recruit.setMember(member);
        return recruitRepository.save(recruit);
    }

    // 모집글 상세페이지
    //@Cacheable(value = "recruit", key = "#recruitID")
    @Transactional
    public Recruit getRecruitID(Integer recruitID) {
        Recruit recruit = recruitRepository.findByIdWithReplies(recruitID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid RecruitID: " + recruitID));
        recruit.setViewCount(recruit.getViewCount() + 1);

        // 정렬된 댓글 리스트 설정
        List<Reply> sortedReplies = replyRepository.findRepliesByRecruitIdOrderByReplyDateDesc(recruitID);

        // 기존의 replies 컬렉션을 정렬된 리스트로 교체하지 않고 새 리스트를 추가함
        recruit.getReplies().clear();
        recruit.getReplies().addAll(sortedReplies);

        // 로그 출력
        sortedReplies.forEach(reply -> log.info("Reply ID: {}, Reply Date: {}", reply.getReplyID(), reply.getReplyDate()));

        return recruitRepository.save(recruit);
    }


    // 모집글 수정하기
    @Transactional
    public Recruit updateRecruit(Integer recruitID, RecruitDto recruitDto, String memberID) {
        Recruit existingRecruit = recruitRepository.findById(recruitID).orElseThrow(() -> new IllegalArgumentException("Invalid RecruitID: " + recruitID));
        if (!existingRecruit.getMember().getMemberID().equals(memberID)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        existingRecruit.setRecruitStatus(recruitDto.getRecruitStatus());
        existingRecruit.setRecruitTitle(recruitDto.getRecruitTitle());
        existingRecruit.setRecruitContent(recruitDto.getRecruitContent());
        return recruitRepository.save(existingRecruit);
    }

    // 모집글 삭제
    public void deleteRecruit(Integer recruitID) {
        recruitRepository.deleteById(recruitID);
    }

    // 댓글 달기
    public Reply addReply(Integer recruitID, String replyContent, String memberID) {
        Reply reply = new Reply();
        reply.setMember(memberRepository.findByMemberID(memberID));
        Recruit recruit = recruitRepository.findById(recruitID).orElseThrow(() -> new IllegalArgumentException("Invalid recruitID:" + recruitID));
        reply.setRecruit(recruit);
        reply.setReplyContent(replyContent);
        return replyRepository.save(reply);
    }

    // 댓글 수정하기
    public Reply updateReply(Integer replyID, String replyContent, String memberID) {
        Reply reply = replyRepository.findById(replyID).orElseThrow(() -> new IllegalArgumentException("Invalid replyID:" + replyID));
        if (!reply.getMember().getMemberID().equals(memberID)) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        reply.setReplyContent(replyContent);
        return replyRepository.save(reply);
    }

    // 댓글 삭제하기
    @Transactional
    public void deleteReply(Integer replyID) {
        replyRepository.deleteById(replyID);
    }
}
