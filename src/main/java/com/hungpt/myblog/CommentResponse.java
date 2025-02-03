package com.hungpt.myblog;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hungpt.myblog.entity.Comment}
 */
public record CommentResponse(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt,
                              String content) implements Serializable {
}