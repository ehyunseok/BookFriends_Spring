package com.daney.bookfriends.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "likey")
@Getter
@Setter
@ToString
public class Likey {

    @EmbeddedId
    private LikeyId id;

    @ManyToOne
    @MapsId("userID")
    @JoinColumn(name = "userID")
    private User user;

    @Column(name = "userIP", length = 50)
    private String userIP;

    @ManyToOne
    @MapsId("itemID")
    @JoinColumn(name = "itemID", insertable = false, updatable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name = "recruitID", insertable = false, updatable = false)
    private Recruit recruit;
}
