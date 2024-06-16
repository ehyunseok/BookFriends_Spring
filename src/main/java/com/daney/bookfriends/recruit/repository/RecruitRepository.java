package com.daney.bookfriends.recruit.repository;

import com.daney.bookfriends.entity.Recruit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruitRepository extends JpaRepository<Recruit, Integer> {

    @Query("SELECT r FROM Recruit r " +
            "WHERE r.recruitTitle LIKE %:search% " +
            "OR r.recruitContent LIKE %:search% " +
            "OR r.member.memberID LIKE %:search%")
    Page<Recruit> findByTitleOrContentOrMember(@Param("search") String search, Pageable pageable);

    @Query("SELECT r FROM Recruit r " +
            "WHERE r.recruitStatus = :recruitStatus " +
            "AND (r.recruitTitle LIKE %:search% " +
            "OR r.recruitContent LIKE %:search% " +
            "OR r.member.memberID LIKE %:search%)")
    Page<Recruit> findByRecruitStatusAndTitleOrContentOrMember(@Param("recruitStatus") String recruitStatus, @Param("search") String search, Pageable pageable);


    @Query("SELECT r FROM Recruit r " +
            "LEFT JOIN FETCH r.replies WHERE r.recruitID = :recruitID")
    Optional<Recruit> findByIdWithReplies(@Param("recruitID") Integer recruitID);
}
