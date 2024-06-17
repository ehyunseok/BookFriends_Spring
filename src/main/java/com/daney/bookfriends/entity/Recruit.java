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
@Table(name = "recruit")
@DynamicInsert
public class Recruit/* implements Serializable */{
//    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitID", nullable = false)
    private Integer recruitID;

    @ManyToOne
    @JoinColumn(name = "memberID")
    private Member member;

    @Column(name = "recruitStatus", length = 20)
    private String recruitStatus;

    @Column(name = "recruitTitle", length = 500)
    private String recruitTitle;

    @Column(name = "recruitContent")
    private String recruitContent;

    @Column(name = "registDate")
    private Timestamp registDate;

    @Column(name = "viewCount")
    private Integer viewCount;

    @OneToMany(mappedBy = "recruit", cascade = CascadeType.ALL, orphanRemoval = true/*, fetch = FetchType.LAZY*/)
    private List<Reply> replies = new ArrayList<>();

    public void addReply(Reply reply) {
        replies.add(reply);
        reply.setRecruit(this);
    }
}
