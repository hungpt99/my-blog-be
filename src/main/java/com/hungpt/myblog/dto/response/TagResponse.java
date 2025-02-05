package com.hungpt.myblog.dto.response;

import com.hungpt.myblog.entity.Tag;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hungpt.myblog.entity.Tag}
 */
@Getter
@Setter
@SuperBuilder
public class TagResponse extends AbstractBaseResponse implements Serializable {

    UUID id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
    LocalDateTime deletedAt;
    String deletedBy;
    boolean isDeleted;

    String name;

    /**
     * Maps a Tag entity to a TagResponse DTO.
     *
     * @param entity the Tag entity to convert.
     * @return a TagResponse DTO.
     */
    public static TagResponse fromEntity(Tag entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        return TagResponse.builder() // Using builder to create TagResponse
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .isDeleted(entity.getIsDeleted())
                .name(entity.getName())
                .build();
    }
}
