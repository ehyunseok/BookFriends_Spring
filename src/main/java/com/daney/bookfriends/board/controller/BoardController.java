package com.daney.bookfriends.board.controller;

import com.daney.bookfriends.board.dto.BoardDto;
import com.daney.bookfriends.board.repository.BoardRepository;
import com.daney.bookfriends.board.service.BoardService;
import com.daney.bookfriends.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;

    //게시판 메인 화면 불러오기
    @GetMapping
    public String getBoardList(Model model) {
        List<Board> boardList = boardService.getAllPosts();
        model.addAttribute("boardList", boardList);
        return "board/board";
    }


    // 게시글 작성페이지로
    @GetMapping("/regist")
    public String registPostForm(Model model) {
        model.addAttribute("board", new BoardDto());
        return "board/regist";
    }

    // 게시글 작성
    @PostMapping("/regist")
    public String registPost(@ModelAttribute BoardDto boardDto, Principal principal) {// 요청 파라미터를 BoardDto 객체에 바인딩. 인증된 사용자의 정보를 담고있는 Principa 객체
        
        if(principal != null) {
            String memberID = principal.getName();  // principal 객체에서 사용자 이름(memberID)를 가져옴
            boardService.registPost(boardDto, memberID);    //boardService를 사용해 게시글 등록
        }
        return "redirect:/board";     // 나중에 작성한 글 페이지로 이동하게 수정!!!!!!!
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 이미지 업로드
    @PostMapping("/uploadImage")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("uploadFile") MultipartFile uploadFile) {
        log.debug("Upload Image Endpoint Hit");

        File uploadDirPath  = new File(uploadDir);
        log.debug("Upload Directory: {}", uploadDir);


        // 파일이름을 생성(현재 시간을 기준으로 고유한 파일 생성)
        String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
        File file = new File(uploadDirPath + File.separator + fileName);
        log.debug("Generated File Name: {}", fileName);

        try {
            // 디렉토리에 파일을 저장함
            uploadFile.transferTo(file);
            log.debug("File uploaded successfully to {}", file.getAbsolutePath());
        } catch (Exception e){
            log.error("Error uploading file", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 파일 저장 중 오류 발생 시 http 500 코드 반환
        }

        Map<String, String> result = new HashMap<>();   // 결과를 담을 맵 생성
        result.put("fileName", fileName);   //맵에 파일 이름 추가
        return ResponseEntity.ok(result);   //맵을 JSON 객체로 파일 이름을 반환함
    }


    //게시물 상세페이지
    @GetMapping("/post/{postID}")
    public String getPost(@PathVariable("postID") Integer postID, Model model) {
        Board board = boardService.getPostById(postID);
        if(board == null) {
            return "redirect:/board";
        }

        // 현재 로그인된 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentMemberID = null;
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            currentMemberID = userDetails.getUsername();
        }
        model.addAttribute("currentMemberID", currentMemberID);//로그인된 사용자의 ID 전달

        // 게시글 전달
        model.addAttribute("board", board);
        return "board/post";
    }

// 게시글 수정하기
    // 수정 화면
    @GetMapping("/update/{postID}")
    public String updatePostForm(@PathVariable("postID") Integer postID, Model model) {

        // postID로 조회하여 DB에서 해당 게시글을 가져옴
        Board board = boardService.getPostById(postID);

        // Board 객체를 BoardDto로 변환하여 모델에 추가
        BoardDto boardDto = new BoardDto();
        boardDto.setPostID(board.getPostID());
        boardDto.setPostCategory(board.getPostCategory());
        boardDto.setPostTitle(board.getPostTitle());
        boardDto.setPostContent(board.getPostContent());

        model.addAttribute("board", boardDto);

        return "board/update";
    }

    //게시글 수정처리
    @PostMapping("/update/{postID}")
    public String updatePost(@PathVariable("postID") Integer postID,
                             @ModelAttribute BoardDto boardDto, Principal principal){
        boardService.updatePost(postID, boardDto, principal.getName());
        return "redirect:/board/post/" + postID;
    }

    // 게시글 삭제
    @PostMapping("/delete/{postID}")
    public String deletePost(@PathVariable("postID") Integer postID){
        boardService.deletePost(postID);
        return "redirect:/board";
    }






}
