package com.amberlight.cloud.svcpost.post;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "posts")
@Data
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "title")
    private String title;

    @Column(name = "preview_text")
    private String previewText;

    @Column(name = "content")
    private String content;

//    public Post() {}
//
//    public Post(Long userId, String title) {
//        this.userId = userId;
//        this.title = title;
//    }

}
