//package com.daney.bookfriends.likey.service;
//
//import com.daney.bookfriends.board.repository.BoardRepository;
//import com.daney.bookfriends.entity.*;
//import com.daney.bookfriends.reply.repository.ReplyRepository;
//import com.daney.bookfriends.likey.repository.LikeyRepository;
//import com.daney.bookfriends.review.repository.ReviewRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Caching;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class LikeyService {
//
//    @Autowired
//    private LikeyRepository likeyRepository;
//
//    @Autowired
//    private BoardRepository boardRepository;
//
//    @Autowired
//    private ReplyRepository replyRepository;
//
//    @Autowired
//    private ReviewRepository reviewRepository;
//
//    @Autowired
////    private RedisTemplate<String, Object> redisTemplate;
//
//    // 추천 기능
////    @Caching(evict = {
////            @CacheEvict(value = "board", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).POST"),
////            @CacheEvict(value = "reply", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).REPLY"),
////            @CacheEvict(value = "review", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).REVIEW")
////    })
//    public boolean toggleLike(String memberID, ItemType itemType, Integer itemID){
//        LikeyId likeyId = new LikeyId(memberID, itemType, itemID);
//        Optional<Likey> optionalLikey = likeyRepository.findById(likeyId);
//
//        if(optionalLikey.isPresent()){  //이미 추천 상태면 추천 취소
//            likeyRepository.deleteById(likeyId);
//            updateLikeCount(itemType, itemID, -1);
//            return false;
//        } else {
//            Likey likey = new Likey();
//            likey.setMemberID(memberID);
//            likey.setItemType(itemType);
//            likey.setItemID(itemID);
//            likeyRepository.save(likey);
//            updateLikeCount(itemType, itemID, 1);
//            return true;    // 추천하기
//        }
//    }
//
//
//    // 추천 수 업데이트 메소드
//    @Caching(evict = {
//            @CacheEvict(value = "board", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).POST"),
//            @CacheEvict(value = "reply", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).REPLY"),
//            @CacheEvict(value = "review", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).REVIEW")
//    })
//    public void updateLikeCount(ItemType itemType, Integer itemID, int delta) {
//        if (itemType == ItemType.POST) {    // 자유게시판 게시글 추천
//            Optional<Board> optionalBoard = boardRepository.findById(itemID);
//            if (optionalBoard.isPresent()) {
//                Board board = optionalBoard.get();
//                board.setLikeCount(board.getLikeCount() + delta);
//                boardRepository.save(board);
//            }
//        } else if (itemType == ItemType.REPLY) {    // 자유게시판 게시글 댓글 추천
//            Optional<Reply> optionalReply = replyRepository.findById(itemID);
//            if (optionalReply.isPresent()) {
//                Reply reply = optionalReply.get();
//                reply.setLikeCount(reply.getLikeCount() + delta);
//                replyRepository.save(reply);
//            }
//        } else if(itemType == ItemType.REVIEW){ // 서평 추천
//            Optional<Review> optionalReview = reviewRepository.findById(itemID);
//            if(optionalReview.isPresent()){
//                Review review = optionalReview.get();
//                review.setLikeCount(review.getLikeCount() + delta);
//                reviewRepository.save(review);
//            }
//        }
//    }
//
//    private void updateCache(String cacheName, Integer itemID, Object entity) {
//        String cacheKey = cacheName + "::" + itemID;
//        redisTemplate.opsForValue().set(cacheKey, entity);
//    }
//}

///////////////////////////////////////////////////////////////////////////////////////////
package com.daney.bookfriends.likey.service;

import com.daney.bookfriends.board.repository.BoardRepository;
import com.daney.bookfriends.entity.*;
import com.daney.bookfriends.reply.repository.ReplyRepository;
import com.daney.bookfriends.likey.repository.LikeyRepository;
import com.daney.bookfriends.review.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeyService {

    @Autowired
    private LikeyRepository likeyRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    // Redis 추가
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 추천 기능
    @Caching(evict = {
            @CacheEvict(value = "board", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).POST"),
            @CacheEvict(value = "reply", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).REPLY"),
            @CacheEvict(value = "review", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).REVIEW")
    }) // 캐시 무효화 적용
    public boolean toggleLike(String memberID, ItemType itemType, Integer itemID){
        LikeyId likeyId = new LikeyId(memberID, itemType, itemID);
        Optional<Likey> optionalLikey = likeyRepository.findById(likeyId);

        if(optionalLikey.isPresent()){  //이미 추천 상태면 추천 취소
            likeyRepository.deleteById(likeyId);
            updateLikeCount(itemType, itemID, -1);
            return false;
        } else {
            Likey likey = new Likey();
            likey.setMemberID(memberID);
            likey.setItemType(itemType);
            likey.setItemID(itemID);
            likeyRepository.save(likey);
            updateLikeCount(itemType, itemID, 1);
            return true;    // 추천하기
        }
    }


    // 추천 수 업데이트 메소드
    @Caching(evict = {
            @CacheEvict(value = "board", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).POST"),
            @CacheEvict(value = "reply", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).REPLY"),
            @CacheEvict(value = "review", key = "#itemID", condition = "#itemType == T(com.daney.bookfriends.entity.ItemType).REVIEW")
    }) // 캐시 무효화 적용
    public void updateLikeCount(ItemType itemType, Integer itemID, int delta) {
        if (itemType == ItemType.POST) {    // 자유게시판 게시글 추천
            Optional<Board> optionalBoard = boardRepository.findById(itemID);
            if (optionalBoard.isPresent()) {
                Board board = optionalBoard.get();
                board.setLikeCount(board.getLikeCount() + delta);
                boardRepository.save(board);
                updateCache("board", itemID, board); // 캐시 업데이트
            }
        } else if (itemType == ItemType.REPLY) {    // 자유게시판 게시글 댓글 추천
            Optional<Reply> optionalReply = replyRepository.findById(itemID);
            if (optionalReply.isPresent()) {
                Reply reply = optionalReply.get();
                reply.setLikeCount(reply.getLikeCount() + delta);
                replyRepository.save(reply);
                updateCache("reply", itemID, reply); // 캐시 업데이트
            }
        } else if(itemType == ItemType.REVIEW){ // 서평 추천
            Optional<Review> optionalReview = reviewRepository.findById(itemID);
            if(optionalReview.isPresent()){
                Review review = optionalReview.get();
                review.setLikeCount(review.getLikeCount() + delta);
                reviewRepository.save(review);
                updateCache("review", itemID, review); // 캐시 업데이트
            }
        }
    }

    // 캐시 업데이트 메소드
    private void updateCache(String cacheName, Integer itemID, Object entity) {
        String cacheKey = cacheName + "::" + itemID;
        redisTemplate.opsForValue().set(cacheKey, entity);
    }

}