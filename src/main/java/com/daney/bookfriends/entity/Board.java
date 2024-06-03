package com.daney.bookfriends.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.Set;

@Entity
@DiscriminatorValue("POST")
@Getter
@Setter
@ToString
public class Board extends Item {

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @Column(name = "postCategory", length = 20)
    private String postCategory;

    @Column(name = "postTitle", length = 50)
    private String postTitle;

    @Column(name = "postContent", columnDefinition = "TEXT")
    private String postContent;

    @Column(name = "viewCount")
    private Integer viewCount;

    @Column(name = "likeCount")
    private Integer likeCount;

    @Column(name = "postDate", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate = new Date();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Likey> likeys;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reply> replies;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<File> files;
}
