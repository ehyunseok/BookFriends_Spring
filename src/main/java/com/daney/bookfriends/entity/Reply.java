package com.daney.bookfriends.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.Set;

@Entity
@DiscriminatorValue("REPLY")
@Getter
@Setter
@ToString
public class Reply extends Item {

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "postID")
    private Board post;

    @ManyToOne
    @JoinColumn(name = "reviewID")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "recruitID")
    private Recruit recruit;

    @Column(name = "replyContent", columnDefinition = "TEXT")
    private String replyContent;

    @Column(name = "likeCount")
    private Integer likeCount;

    @Column(name = "replyDate", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date replyDate = new Date();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Likey> likeys;

    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reply> replies;
}
