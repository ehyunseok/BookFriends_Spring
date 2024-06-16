package com.daney.bookfriends.board.service;

import com.daney.bookfriends.board.dto.BoardDto;
import com.daney.bookfriends.board.repository.BoardRepository;
import com.daney.bookfriends.entity.Board;
import com.daney.bookfriends.entity.Member;
import com.daney.bookfriends.entity.Reply;
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
import com.daney.bookfriends.Member.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ReplyRepository replyRepository;

    // 모든 게시글 리스트 가져오기
    @Cacheable(value = "boardList", key = "#page + '-' + #size + '-' + #postCategory + '-' + #searchType + '-' + #search")
    public Page<Board> getFilteredPosts(int page, int size, String postCategory, String searchType, String search) {
        Pageable pageable;
        switch (searchType) {
            case "조회수순":
                pageable = PageRequest.of(page - 1, size, Sort.by("viewCount").descending());
                break;
            case "추천순":
                pageable = PageRequest.of(page - 1, size, Sort.by("likeCount").descending());
                break;
            default:
                pageable = PageRequest.of(page - 1, size, Sort.by("postDate").descending());
                break;
        }

        if ("전체".equals(postCategory)) {
            return boardRepository.findByTitleOrContentOrMember("%" + search + "%", pageable);
        } else {
            return boardRepository.findByCategoryAndTitleOrContentOrMember(postCategory, "%" + search + "%", pageable);
        }
    }

    // 게시글 작성하기
    public Board registPost(BoardDto boardDto, String memberID) {

        // model mapper 라이브러리를 통해 BoardDto 객체를 Board 엔티티 객체로 변환
        Board board = modelMapper.map(boardDto, Board.class);


        //회원 정보 설정 memberID로 Member객체를 찾아서 Board entity에 설정
        Member member = memberRepository.findById(memberID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberID));
        board.setMember(member);

        // Board 엔티티를 db에 저장함
        return boardRepository.save(board);
    }

    //게시글 상세페이지 보기
    @Cacheable(value = "board", key = "#postID")
    @Transactional
    public Board getPostById(Integer postID) {
        Board board = boardRepository.findById(postID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postID));

        board.setViewCount(board.getViewCount() + 1);   // 조회수 1씩 증가

        // 정렬된 댓글 리스트 설정
        List<Reply> sortedReplies = replyRepository.findRepliesByBoardIdOrderByReplyDateDesc(postID);
        board.setReplies(sortedReplies);

        // 로그 출력
        sortedReplies.forEach(reply -> log.info("Reply ID: {}, Reply Date: {}", reply.getReplyID(), reply.getReplyDate()));

        return board;
    }

    //게시글 수정하기
    @Transactional
    public Board updatePost(Integer postID, BoardDto boardDto, String memberID) {
        Board existingBoard = boardRepository.findById(postID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postID));

        // 작성자 확인
        if(!existingBoard.getMember().getMemberID().equals(memberID)){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        // 수정할 데이터 설정
        existingBoard.setPostCategory(boardDto.getPostCategory());
        existingBoard.setPostTitle(boardDto.getPostTitle());
        existingBoard.setPostContent(boardDto.getPostContent());

        return boardRepository.save(existingBoard);
    }

    //게시글 삭제
    public void deletePost(Integer postID) {
        boardRepository.deleteById(postID);
    }


    // 댓글 달기
    public Reply addReply(Integer postID, String replyContent, String memberID) {

        Reply reply = new Reply();

        // 댓글 작성자를 현재 사용자로 설정
        reply.setMember(memberRepository.findByMemberID(memberID));

        // 현재 게시글의 댓글으로 설정
        Board board = boardRepository.findById(postID)
                .orElseThrow(()->new IllegalArgumentException("Invalid postID:" + postID));
        reply.setBoard(board);
        reply.setReplyContent(replyContent);
        // Reply entity를 db에 저장
        return replyRepository.save(reply);

    }

    //댓글 수정하기
    public Reply updateReply(Integer replyID, String replyContent, String memberID) {
        Reply reply = replyRepository.findById(replyID)
                .orElseThrow(()->new IllegalArgumentException("Invalid replyID:" + replyID));
        if(!reply.getMember().getMemberID().equals(memberID)){
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        reply.setReplyContent(replyContent);
        return replyRepository.save(reply);
    }

    // 댓글 삭제
    public void deleteReply(Integer replyID) {
        replyRepository.deleteById(replyID);
    }

}
