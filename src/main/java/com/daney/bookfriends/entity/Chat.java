package com.daney.bookfriends.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "chat")
@Getter
@Setter
@ToString
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatID")
    private Integer chatID;

    @ManyToOne
    @JoinColumn(name = "senderID")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverID")
    private User receiver;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "chatTime", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date chatTime = new Date();

    @Column(name = "chatRead")
    private Boolean chatRead = false;
}

