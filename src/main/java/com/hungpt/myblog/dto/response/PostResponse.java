package com.hungpt.myblog.dto.response;

import com.hungpt.myblog.dto.enums.PostStatus;
import com.hungpt.myblog.entity.Config;
import com.hungpt.myblog.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hungpt.myblog.entity.Post}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse extends AbstractBaseResponse implements Serializable, IResponse<PostResponse, Post> {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String content;
    private PostStatus status;
    private long viewCount;
    private long commentCount;

    // Implement the fromEntity method
    @Override
    public PostResponse fromEntity(Post entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        return PostResponse.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .title(entity.getTitle())
                .content(entity.getContent())
                .status(PostStatus.valueOf(entity.getStatus().name())) // Assuming PostStatus is an enum that maps to entity's status
                .viewCount(entity.getViewCount())
                .commentCount(entity.getCommentCount())
                .build();
    }
}
