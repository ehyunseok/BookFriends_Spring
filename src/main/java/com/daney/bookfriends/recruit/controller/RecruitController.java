package com.daney.bookfriends.recruit.controller;

import com.daney.bookfriends.entity.Recruit;
import com.daney.bookfriends.recruit.dto.RecruitDto;
import com.daney.bookfriends.recruit.service.RecruitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/recruit")
public class RecruitController {

    @Autowired
    private RecruitService recruitService;

    //모집 메인 리스트
    @GetMapping
    public String getRecruitList(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 @RequestParam(defaultValue = "전체") String recruitStatus,
                                 @RequestParam(defaultValue = "최신순") String searchType,
                                 @RequestParam(defaultValue = "") String search,
                                 Model model) {
        Page<Recruit> recruits = recruitService.getFilteredRecruits(page, size, recruitStatus, searchType, search);
        model.addAttribute("recruits", recruits);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", recruits.getTotalPages());
        model.addAttribute("totalItems", recruits.getTotalElements());
        model.addAttribute("size", size);
        return "recruit/recruit";
    }

    //모집글 작성폼
    @GetMapping("/regist")
    public String registForm(Model model) {
        model.addAttribute("recruit", new RecruitDto());
        return "recruit/regist";
    }

    //모집글 작성하기
    @PostMapping("/regist")
    public String regist(@ModelAttribute RecruitDto recruitDto, Principal principal) {
        if(principal != null) {
            String memberID = principal.getName();
            recruitService.registRecruit(recruitDto, memberID);
        }
        return "redirect:/recruit";
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

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

    //모집글 상세페이지
    @GetMapping("/post/{recruitID}")
    public String post(@PathVariable("recruitID") Integer recruitID, Model model) {
        Recruit recruit = recruitService.getRecruitID(recruitID);
        if(recruit == null) {
            return "redirect:/recruit";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentMemberID = authentication.getName();
        if(authentication != null && authentication.getPrincipal() instanceof UserDetails){
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            currentMemberID = userDetails.getUsername();
        }
        model.addAttribute("currentMemberID", currentMemberID);
        model.addAttribute("recruit", recruit);
        return "recruit/post";
    }

//모집글 수정
    //모집글 수정폼
    @GetMapping("/update/{recruitID}")
    public String update(@PathVariable("recruitID") Integer recruitID, Model model) {
        Recruit recruit = recruitService.getRecruitID(recruitID);

        RecruitDto recruitDto = new RecruitDto();
        recruitDto.setRecruitID(recruit.getRecruitID());
        recruitDto.setRecruitStatus(recruit.getRecruitStatus());
        recruitDto.setRecruitTitle(recruit.getRecruitTitle());
        recruitDto.setRecruitContent(recruit.getRecruitContent());

        model.addAttribute("recruit", recruitDto);
        return "recruit/update";
    }

    // 모집글 수정처리
    @PostMapping("/update/{recruitID}")
    public String update(@PathVariable("recruitID") Integer recruitID,
            @ModelAttribute RecruitDto recruitDto, Principal principal) {
        recruitService.updateRecruit(recruitID, recruitDto, principal.getName());
        return "redirect:/recruit/post/" + recruitID;
    }

// 모집글 삭제
    @DeleteMapping("/delete/{recruitID}")
    public String deleteRecruit(@PathVariable("recruitID") Integer recruitID) {
        recruitService.deleteRecruit(recruitID);
        return "redirect:/recruit";
    }

// 댓글 달기
    @PostMapping("/registReply")
    public String addReply(@RequestParam("recruitID") Integer recruitID,
                           @RequestParam("replyContent") String replyContent, Principal principal) {
        if (principal != null) {
            String memberID = principal.getName();
            recruitService.addReply(recruitID, replyContent, memberID);
        }
        return "redirect:/recruit/post/" + recruitID;
    }

// 댓글 수정하기
    @PostMapping("/updateReply")
    public String updateReply(@RequestParam("replyID") Integer replyID,
                              @RequestParam("recruitID") Integer recruitID,
                              @RequestParam("replyContent") String replyContent,
                              Principal principal) {
        if(principal != null) {
            String memberID = principal.getName();
            recruitService.updateReply(replyID, replyContent, memberID);
        }
        return "redirect:/recruit/post/" + recruitID;
    }

// 댓글 삭제
    @DeleteMapping("/deleteReply/{recruitID}/{replyID}")
    public String deleteReply(@PathVariable("replyID") Integer replyID, @PathVariable("recruitID") Integer recruitID){
        recruitService.deleteReply(replyID);
        return "redirect:/recruit/post/" + recruitID;
    }


}
