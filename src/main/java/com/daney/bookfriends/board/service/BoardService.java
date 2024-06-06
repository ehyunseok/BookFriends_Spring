package com.daney.bookfriends.board.service;

import com.daney.bookfriends.board.dto.BoardDto;
import com.daney.bookfriends.board.repository.BoardRepository;
import com.daney.bookfriends.entity.Board;
import com.daney.bookfriends.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.daney.bookfriends.Member.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;


    // [메인페이지] 상위 5개의 게시글 리스트불러오기 메소드
    public List<BoardDto> getTop5Boards() {
        List<Board> boards = boardRepository.findTop5ByOrderByLikeCountDesc();
        return boards.stream()
                .map(board -> modelMapper.map(board, BoardDto.class))
                .collect(Collectors.toList());
    }

    // 모든 게시글 리스트 가져오기
    public List<Board> getAllPosts() {
        return boardRepository.findAll();
    }


    public Board registPost(BoardDto boardDto, String memberID) {

        // model mapper 라이브러리를 통해 BoardDto 객체를 Board 엔티티 객체로 변환
        Board board = modelMapper.map(boardDto, Board.class);


        //회원 정보 설정 memberID로 Member객체를 찾아서 Board entity에 설정
        Member member = memberRepository.findById(memberID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberID));
        board.setMember(member);
        
        // Board 에티티를 db에 저장함
        return boardRepository.save(board);
    }

    //게시글 상세페이지 보기
    @Transactional
    public Board getPostById(Integer postID) {
        Board board = boardRepository.findById(postID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postID));

        board.setViewCount(board.getViewCount() + 1);   // 조회수 1씩 증가

        return board;
    }

    //게시글 수정하기
    @Transactional
    public void updatePost(Integer postID, BoardDto boardDto, String memberID) {
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

        boardRepository.save(existingBoard);
    }

    @Transactional
    public void deletePost(Integer postID) {
        boardRepository.deleteById(postID);
    }
}
