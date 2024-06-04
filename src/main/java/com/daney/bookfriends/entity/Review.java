package com.daney.bookfriends.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "review")
@DynamicInsert
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewID", nullable = false)
    private Integer reviewID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @Column(name = "bookName", length = 500)
    private String bookName;

    @Column(name = "authorName", length = 100)
    private String authorName;

    @Column(name = "publisher", length = 20)
    private String publisher;

    @Column(name = "category", length = 20)
    private String category;

    @Column(name = "reviewTitle", length = 500)
    private String reviewTitle;

    @Column(name = "reviewContent")
    private String reviewContent;

    @Column(name = "reviewScore")
    private Integer reviewScore;

    @Column(name = "registDate")
    private Timestamp registDate;

    @Column(name = "likeCount")
    private Integer likeCount;

    @Column(name = "viewCount")
    private Integer viewCount;
}
