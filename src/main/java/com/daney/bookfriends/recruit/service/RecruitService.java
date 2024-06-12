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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<Recruit> getFilteredRecruits(int page, int size, String recruitStatus, String searchType, String search) {

        Pageable pageable;

        switch (searchType){
            case "조회수순":
                pageable = PageRequest.of(page - 1, size, Sort.by("viewCount").descending());
                break;
            default:
                pageable = PageRequest.of(page - 1, size, Sort.by("registDate").descending());
                break;
        }

        if("전체".equals(recruitStatus)){
            return recruitRepository.findByTitleOrContentOrMember("%" + search + "%", pageable);
        } else {
            return recruitRepository.findByRecruitStatusAndTitleOrContentOrMember(recruitStatus, "%" + search + "%", pageable);
        }
    }


    // 모집글 작성하기
    public Recruit registRecruit(RecruitDto recruitDto, String memberID) {
        Recruit recruit = modelMapper.map(recruitDto, Recruit.class);

        Member member = memberRepository.findById(memberID)
                .orElseThrow(()-> new IllegalArgumentException("Invalid member ID: " + memberID));
        recruit.setMember(member);

        return recruitRepository.save(recruit);
    }

    // 모집글 상세페이지
    @Transactional
    public Recruit getRecruitID(Integer recruitID) {
        Recruit recruit = recruitRepository.findById(recruitID)
                .orElseThrow(()->new IllegalArgumentException("Invalid RecuritID: "+ recruitID));
        recruit.setViewCount(recruit.getViewCount() + 1);
        return recruit;
    }

    // 모집글 수정하기
    @Transactional
    public void updateRecruit(Integer recruitID, RecruitDto recruitDto, String memberID) {
        Recruit existingRecruit = recruitRepository.findById(recruitID)
                .orElseThrow(()->new IllegalArgumentException("Invalid RecruitID: "+ recruitID));

        if(!existingRecruit.getMember().getMemberID().equals(memberID)){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        existingRecruit.setRecruitStatus(recruitDto.getRecruitStatus());
        existingRecruit.setRecruitTitle(recruitDto.getRecruitTitle());
        existingRecruit.setRecruitContent(recruitDto.getRecruitContent());

        recruitRepository.save(existingRecruit);
    }

    // 모집글 삭제
    public void deleteRecruit(Integer recruitID) {
        recruitRepository.deleteById(recruitID);
    }

    //댓글 달기
    public Reply addReply(Integer recruitID, String replyContent, String memberID) {
        Reply reply = new Reply();

        // 댓글 작성자를 현재 사용자로 설정
        reply.setMember(memberRepository.findByMemberID(memberID));

        // 현재 게시글의 댓글으로 설정
        Recruit recruit = recruitRepository.findById(recruitID)
                .orElseThrow(()->new IllegalArgumentException("Invalid recruitID:" + recruitID));
        reply.setRecruit(recruit);
        reply.setReplyContent(replyContent);
        // Reply entity를 db에 저장
        return replyRepository.save(reply);
    }

    //댓글 수정하기
    public void updateReply(Integer replyID, String replyContent, String memberID) {
        Reply reply = replyRepository.findById(replyID)
                .orElseThrow(()->new IllegalArgumentException("Invalid replyID:" + replyID));
        if(!reply.getMember().getMemberID().equals(memberID)){
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        reply.setReplyContent(replyContent);
        replyRepository.save(reply);
    }

    //댓글 삭제하기
    public void deleteReply(Integer replyID) {
        replyRepository.deleteById(replyID);
    }
}