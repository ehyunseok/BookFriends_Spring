package com.daney.bookfriends.board.service;

import com.daney.bookfriends.board.dto.BoardDto;
import com.daney.bookfriends.board.repository.BoardRepository;
import com.daney.bookfriends.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<BoardDto> getTop5Boards() {
        List<Board> boards = boardRepository.findTop5ByOrderByLikeCountDesc();
        return boards.stream()
                .map(board -> modelMapper.map(board, BoardDto.class))
                .collect(Collectors.toList());
    }
}
