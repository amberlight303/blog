package com.amberlight.blog.gateway.client.post;


import lombok.Data;


@Data
public class Post {

    private Long id;
    private Long userId;
    private String title;
    private String previewText;
    private String content;

}
