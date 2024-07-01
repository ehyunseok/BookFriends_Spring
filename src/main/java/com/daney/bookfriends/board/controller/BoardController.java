package com.daney.bookfriends.board.controller;

import com.daney.bookfriends.board.dto.BoardDto;
import com.daney.bookfriends.board.service.BoardService;
import com.daney.bookfriends.entity.Board;
import com.daney.bookfriends.entity.ItemType;
import com.daney.bookfriends.likey.service.LikeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;
    @Autowired
    private LikeyService likeyService;


    //게시판 메인 화면 불러오기
    @GetMapping
    public String getBoardList(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(defaultValue = "전체") String postCategory,
                               @RequestParam(defaultValue = "최신순") String searchType,
                               @RequestParam(defaultValue = "") String search,
                               Model model) {
        Page<Board> boardList = boardService.getFilteredPosts(page, size, postCategory, searchType, search);
        model.addAttribute("boardList", boardList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardList.getTotalPages());
        model.addAttribute("totalItems", boardList.getTotalElements());
        model.addAttribute("size", size);
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
        return "redirect:/board";
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 이미지 업로드
    @PostMapping(value = "/uploadImage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("uploadFile") MultipartFile uploadFile) {
        log.debug("Upload Image Endpoint Hit");

        // 업로드 디렉토리 경로를 객체로 생성
        File uploadDirPath = new File(uploadDir);
        if (!uploadDirPath.exists()) {
            boolean created = uploadDirPath.mkdirs();
            log.debug("Created upload directory: {}", created);
        }

        log.debug("Upload Directory: {}", uploadDir);

        // 파일 이름을 생성
        String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
        File file = new File(uploadDirPath, fileName);
        log.debug("Generated File Name: {}", fileName);


        try {
            // 디렉토리에 파일을 저장
            uploadFile.transferTo(file);
            log.debug("File uploaded successfully to {}", file.getAbsolutePath());
        } catch (Exception e) {
            log.error("Error uploading file", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // 파일 저장 중 오류 발생 시 http 500 코드 반환
        }

        Map<String, String> result = new HashMap<>();
        result.put("fileName", "/uploads/"+ fileName);  // 맵에 파일 이름 추가
        return ResponseEntity.ok(result);  // 맵을 JSON 객체로 반환
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
    @DeleteMapping("/delete/{postID}")
    public String deletePost(@PathVariable("postID") Integer postID){
        boardService.deletePost(postID);
        return "redirect:/board";
    }


    // 댓글 달기
    @PostMapping("/registReply")
    public String addReply(@RequestParam("postID") Integer postID,
                           @RequestParam("replyContent") String replyContent, Principal principal) {
        if (principal != null) {
            String memberID = principal.getName();
            boardService.addReply(postID, replyContent, memberID);
        }
        return "redirect:/board/post/" + postID;
    }

    //댓글 수정하기
    @PostMapping("/updateReply")
    public String updateReply(@RequestParam("replyID") Integer replyID,
                              @RequestParam("postID") Integer postID,
                              @RequestParam("replyContent") String replyContent,
                              Principal principal) {

        if(principal != null) {
            String memberID = principal.getName();
            boardService.updateReply(replyID, replyContent, memberID);
        }

        return "redirect:/board/post/" + postID;
    }


    //댓글 삭제
    @DeleteMapping("/deleteReply/{postID}/{replyID}")
    public String deleteReply(@PathVariable("replyID") Integer replyID, @PathVariable("postID") Integer postID) {
        boardService.deleteReply(replyID);
        return "redirect:/board/post/" + postID;
    }


    // 게시글 추천하기
    @PostMapping("/likePost/{postID}")
    @ResponseBody
    public String likePost(@PathVariable("postID") Integer postID, @RequestParam("memberID") String memberID){
        boolean isLiked = likeyService.toggleLike(memberID, ItemType.POST, postID);
        return isLiked ? "liked" : "unliked";
    }

    // 게시글 속 댓글 추천하기
    @PostMapping("/likeReply/{replyID}")
    @ResponseBody
    public String likeReply(@PathVariable("replyID") Integer replyID, @RequestParam("memberID") String memberID) {
        boolean isLiked = likeyService.toggleLike(memberID, ItemType.REPLY, replyID);
        return isLiked ? "liked" : "unliked";
    }


}
