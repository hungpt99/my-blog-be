package com.hungpt.myblog.dto.response;

import com.hungpt.myblog.entity.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hungpt.myblog.entity.Config}
 */
@Getter
@Setter
@SuperBuilder
public class ConfigResponse extends AbstractBaseResponse implements Serializable {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String key;
    private String value;
    private String description;
    private String type;

    // Implement the fromEntity method to convert from entity to DTO using builder
    public static ConfigResponse fromEntity(Config entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        return ConfigResponse.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .key(entity.getKey())
                .value(entity.getValue())
                .description(entity.getDescription())
                .type(entity.getType())
                .build();
    }
}
