package com.daney.bookfriends.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "review")
@DynamicInsert
public class Review implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewID", nullable = false)
    private Integer reviewID;

    @ManyToOne
    @JoinColumn(name = "memberID")
    private Member member;

    @Column(name = "bookName")
    private String bookName;

    @Column(name = "authorName")
    private String authorName;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "category")
    private String category;

    @Column(name = "reviewTitle")
    private String reviewTitle;

    @Column(name = "reviewContent")
    private String reviewContent;

    @Column(name = "reviewScore")
    private Integer reviewScore;

    @Column(name = "registDate")
    private Timestamp registDate;

    @Column(name = "likeCount", columnDefinition = "INTEGER DEFAULT 0")
    private Integer likeCount;

    @Column(name = "viewCount", columnDefinition = "INTEGER DEFAULT 0")
    private Integer viewCount;
}
