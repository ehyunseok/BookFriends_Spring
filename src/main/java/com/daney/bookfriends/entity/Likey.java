package com.daney.bookfriends.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "likey")
@IdClass(LikeyId.class)
public class Likey implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "memberID", nullable = false)
    private String memberID;  // member 타입 대신 memberID를 직접 사용

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "itemType", nullable = false)
    private ItemType itemType;

    @Id
    @Column(name = "itemID", nullable = false)
    private Integer itemID;
}
