package com.daney.bookfriends.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "board")
@DynamicInsert
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postID", nullable = false)
    private Integer postID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @Column(name = "postCategory", length = 20)
    private String postCategory;

    @Column(name = "postTitle", length = 50)
    private String postTitle;

    @Column(name = "postContent")
    private String postContent;

    @Column(name = "viewCount")
    private Integer viewCount;

    @Column(name = "likeCount")
    private Integer likeCount;

    @Column(name = "postDate")
    private Timestamp postDate;
}
