package com.daney.bookfriends.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "reply")
@DynamicInsert
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replyID", nullable = false)
    private Integer replyID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "postID")
    private Board post;

    @Column(name = "replyContent")
    private String replyContent;

    @Column(name = "likeCount")
    private Integer likeCount;

    @Column(name = "replyDate")
    private Timestamp replyDate;

    @ManyToOne
    @JoinColumn(name = "recruitID")
    private Recruit recruit;
}
