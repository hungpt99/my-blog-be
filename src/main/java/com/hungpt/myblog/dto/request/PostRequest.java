package com.hungpt.myblog.dto.request;

import com.hungpt.myblog.dto.enums.PostStatus;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hungpt.myblog.entity.Post}
 */
@Value
public class PostRequest implements Serializable {
    UUID id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String title;
    String content;
    PostStatus status;
    long viewCount;
    long commentCount;
}