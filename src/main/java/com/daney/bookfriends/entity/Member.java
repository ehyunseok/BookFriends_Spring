package com.daney.bookfriends.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Getter
@Setter
@Entity
@Table(name = "member")
@DynamicInsert
public class Member {

    @Id
    @Column(name="memberID")
    private String memberID;

    @Column(name="memberPassword")
    private String memberPassword;

    @Column(name="memberEmail")
    private String memberEmail;

    @Column(name="memberEmailChecked")
    private Boolean memberEmailChecked;
}
