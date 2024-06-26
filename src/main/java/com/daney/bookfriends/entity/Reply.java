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
@Table(name = "reply")
@DynamicInsert
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replyID", nullable = false)
    private Integer replyID;

    @ManyToOne
    @JoinColumn(name = "memberID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "postID")
    private Board board;

    public void setBoard(Board board) {
        this.board = board;
        if(!board.getReplies().contains(this)) {
            board.getReplies().add(this);
        }
    }


    @ManyToOne
    @JoinColumn(name = "recruitID")
    private Recruit recruit;

    public void setRecruit(Recruit recruit) {
        this.recruit = recruit;
        if(!recruit.getReplies().contains(this)) {
            recruit.getReplies().add(this);
        }
    }

    @Column(name = "replyContent")
    private String replyContent;

    @Column(name = "likeCount", columnDefinition = "INTEGER DEFAULT 0")
    private Integer likeCount;

    @Column(name = "replyDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp replyDate;
}
