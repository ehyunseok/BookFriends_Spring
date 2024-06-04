package com.daney.bookfriends.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fileID", nullable = false)
    private Integer fileID;

    @Column(name = "fileName", nullable = false, length = 255)
    private String fileName;

    @Column(name = "fileOriginName", nullable = false, length = 255)
    private String fileOriginName;

    @Column(name = "filePath", nullable = false, length = 255)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "recruitID")
    private Recruit recruit;

    @ManyToOne
    @JoinColumn(name = "postID")
    private Board post;
}
