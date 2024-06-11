package com.daney.bookfriends.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "chat")
@DynamicInsert
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatID", nullable = false)
    private Integer chatID;

    @ManyToOne
    @JoinColumn(name = "senderID", referencedColumnName = "memberID")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiverID", referencedColumnName = "memberID")
    private Member receiver;

    @Column(name = "message")
    private String message;

    @Column(name = "chatTime")
    private Timestamp chatTime;

    @Column(name = "chatRead")
    private Boolean chatRead;

}
