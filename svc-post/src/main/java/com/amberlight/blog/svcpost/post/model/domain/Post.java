package com.amberlight.blog.svcpost.post.model.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@org.springframework.data.elasticsearch.annotations.Document(indexName = "posts")
@org.springframework.data.mongodb.core.mapping.Document(collection = "posts")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    public Post(String id) {
        this.id = id;
    }

    @Id
    private String id;

    private Long userId;

    private String title;

    private String previewContent;

    private String content;

    private Date createdDate;

    private Date modifiedDate;

}
