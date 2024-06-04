package com.daney.bookfriends.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "likey")
@IdClass(LikeyId.class)
public class Likey {

    @Id
    @Column(name = "userID", nullable = false)
    private String userID;  // User 타입 대신 userID를 직접 사용

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "itemType", nullable = false)
    private ItemType itemType;

    @Id
    @Column(name = "itemID", nullable = false)
    private Integer itemID;

    @Column(name = "userIP", length = 50)
    private String userIP;
}
