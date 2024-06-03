package com.daney.bookfriends.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Date;
import java.util.Set;

@Entity
@DiscriminatorValue("RECRUIT")
@Getter
@Setter
@ToString
public class Recruit extends Item {

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @Column(name = "recruitStatus", length = 20, nullable = false)
    private String recruitStatus = "모집중";

    @Column(name = "recruitTitle", length = 500)
    private String recruitTitle;

    @Column(name = "recruitContent", columnDefinition = "TEXT")
    private String recruitContent;

    @Column(name = "registDate", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date registDate = new Date();

    @Column(name = "viewCount")
    private Integer viewCount;

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reply> replies;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Likey> likeys;
}
