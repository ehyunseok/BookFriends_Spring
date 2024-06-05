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
@Table(name = "user")
@DynamicInsert
public class User {

    @Id
    @Column(name = "userID", length = 20, nullable = false)
    private String userID;

    @Column(name = "userPassword", length = 64, nullable = false)
    private String userPassword;

    @Column(name = "userEmail", length = 50, nullable = false)
    private String userEmail;

    @Column(name = "userEmailChecked")
    private Boolean userEmailChecked;
}
