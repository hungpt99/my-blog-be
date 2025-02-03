package com.hungpt.myblog.dto.response;

import com.hungpt.myblog.entity.Config;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hungpt.myblog.entity.Config}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigResponse extends AbstractBaseResponse implements Serializable, IResponse<ConfigResponse, Config> {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String key;
    private String value;
    private String description;
    private String type;

    // Constructor
    public ConfigResponse(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt, String key, String value,
                          String description, String type) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.key = key;
        this.value = value;
        this.description = description;
        this.type = type;
    }

    // Implement the fromEntity method to convert from entity to DTO using builder
    @Override
    public ConfigResponse fromEntity(Config entity) {
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
