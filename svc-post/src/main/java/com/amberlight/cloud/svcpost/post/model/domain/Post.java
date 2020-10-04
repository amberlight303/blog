package com.amberlight.cloud.svcpost.post.model.domain;

import com.amberlight.cloud.svcpost.post.model.dto.PostDto;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    @Id
    private String id;

    private Long userId;

    private String title;

    private String previewContent;

    private String content;

    private Date createdDate;

    private Date modifiedDate;

//    public PostDto toDto() {
//        final PostDto dto = new PostDto();
//        dto.setId(this.id);
//        dto.setUserId(this.userId);
//        dto.setTitle(this.title);
//        dto.setPreviewContent(this.previewContent);
//        dto.setContent(this.content);
//        dto.setCreatedDate(LocalDateTime.parse(this.createdDate));
//        dto.setModifiedDate(LocalDateTime.parse(this.modifiedDate));
//        return dto;
//    }

}
