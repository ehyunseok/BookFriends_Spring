package com.daney.bookfriends.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "recruit")
@DynamicInsert
public class Recruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruitID", nullable = false)
    private Integer recruitID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

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
}
