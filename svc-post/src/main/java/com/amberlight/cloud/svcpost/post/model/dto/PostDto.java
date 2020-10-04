package com.amberlight.cloud.svcpost.post.model.dto;


import com.amberlight.cloud.svcpost.post.model.domain.Post;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    private String id;

    private Long userId;

    private String title;

    private String previewContent;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

//    public PostDto(Post post) {
//        this.id = post.getId();
//        this.userId = post.getUserId();
//        this.title = post.getTitle();
//        this.previewContent = post.getPreviewContent();
//        this.content = post.getContent();
//        this.createdDate = LocalDateTime.parse(post.getCreatedDate());
//        this.modifiedDate = LocalDateTime.parse(post.getModifiedDate());
//    }

}
