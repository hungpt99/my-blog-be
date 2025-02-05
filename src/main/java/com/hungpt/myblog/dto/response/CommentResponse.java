package com.hungpt.myblog.dto.response;

import com.hungpt.myblog.entity.Comment;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hungpt.myblog.entity.Comment}
 */
@Getter
@Setter
@SuperBuilder
public class CommentResponse extends AbstractBaseResponse implements Serializable {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime deletedAt;
    private String deletedBy;
    private Boolean isDeleted;
    private String content;

    /**
     * Maps a {@link Comment} entity to a {@link CommentResponse} DTO.
     *
     * @param entity the Comment entity
     * @return the CommentResponse DTO
     */
    public static CommentResponse fromEntity(Comment entity) {
        return CommentResponse.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .isDeleted(entity.getIsDeleted())
                .content(entity.getContent())
                .build();
    }
}
