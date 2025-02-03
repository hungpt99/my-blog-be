package com.hungpt.myblog.dto.response;

import com.hungpt.myblog.entity.Tag;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hungpt.myblog.entity.Tag}
 */
@EqualsAndHashCode(callSuper = true)
@Value
public class TagResponse extends AbstractBaseResponse implements Serializable, IResponse<TagResponse, Tag> {

    UUID id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
    LocalDateTime deletedAt;
    String deletedBy;
    String status;
    String name;

    /**
     * Maps a Tag entity to a TagResponse DTO.
     *
     * @param entity the Tag entity to convert.
     * @return a TagResponse DTO.
     */
    @Override
    public TagResponse fromEntity(Tag entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        return new TagResponse(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getCreatedBy(),
                entity.getUpdatedBy(),
                entity.getDeletedAt(),
                entity.getDeletedBy(),
                entity.getStatus(),
                entity.getName()
        );
    }
}
