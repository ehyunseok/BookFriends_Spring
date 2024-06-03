package com.daney.bookfriends.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.Set;

@Entity
@DiscriminatorValue("REVIEW")
@Getter
@Setter
@ToString
public class Review extends Item {

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

    @Column(name = "reviewContent", columnDefinition = "TEXT")
    private String reviewContent;

    @Column(name = "reviewScore")
    private Integer reviewScore;

    @Column(name = "registDate", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registDate = new Date();

    @Column(name = "likeCount")
    private Integer likeCount;

    @Column(name = "viewCount")
    private Integer viewCount;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Likey> likeys;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reply> replies;
}
