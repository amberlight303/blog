package com.amberlight.blog.svcpost.post.model.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    private String id;

    private Long userId;

    @NotNull
    @Size(min = 3, max = 100)
    private String title;

    @Size(min = 1, max = 1000)
    private String previewContent;

    @NotNull
    @Size(min = 1, max = 5000)
    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

}
