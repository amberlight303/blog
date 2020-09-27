package com.amberlight.cloud.svcpost.post;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Document(collection = "posts")
@Data
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private Long userId;

    private String title;

    private String previewText;

    private String content;

    public Post() {}

    public Post(Long userId, String title, String previewText, String content) {
        this.userId = userId;
        this.title = title;
        this.previewText = previewText;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", previewText='" + previewText + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
