package com.daney.bookfriends.entity;

import com.daney.bookfriends.entity.Reply;
import com.daney.bookfriends.entity.Board;
import com.daney.bookfriends.entity.Likey;
import com.daney.bookfriends.entity.Recruit;
import com.daney.bookfriends.entity.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name = "userID", length = 20, nullable = false)
    private String userID;

    @Column(name = "userPassword", length = 64)
    private String userPassword;

    @Column(name = "userEmail", length = 50)
    private String userEmail;

    @Column(name = "userEmailHash", length = 2048)
    private String userEmailHash;

    @Column(name = "userEmailChecked")
    private Boolean userEmailChecked;

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Board> boards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Recruit> recruits;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reply> replies;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Likey> likeys;


}
