package com.daney.bookfriends.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "file")
@Getter
@Setter
@ToString
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileID")
    private Integer fileID;

    @Column(name = "fileName", length = 255, nullable = false)
    private String fileName;

    @Column(name = "fileOriginName", length = 255, nullable = false)
    private String fileOriginName;

    @Column(name = "filePath", length = 255, nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "recruitID")
    private Recruit recruit;

    @ManyToOne
    @JoinColumn(name = "postID")
    private Board post;
}
