package com.daney.bookfriends.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "board")
@DynamicInsert
public class Board /*implements Serializable*/ {
//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postID", nullable = false)
    private Integer postID;

    @ManyToOne
    @JoinColumn(name = "memberID")
    private Member member;

    @Column(name = "postCategory")
    private String postCategory;

    @Column(name = "postTitle")
    private String postTitle;

    @Column(name = "postContent")
    private String postContent;

    @Column(name = "viewCount", columnDefinition = "INTEGER DEFAULT 0")
    private Integer viewCount;

    @Column(name = "likeCount", columnDefinition = "INTEGER DEFAULT 0")
    private Integer likeCount;

    @Column(name = "postDate")
    private Timestamp postDate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true/*, fetch = FetchType.LAZY*/)
    private List<Reply> replies = new ArrayList<>();

    public void addReply(Reply reply) {
        replies.add(reply);
        reply.setBoard(this);
    }

    @Transient
    private int replyCount;
}
