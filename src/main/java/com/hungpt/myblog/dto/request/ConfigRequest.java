package com.hungpt.myblog.dto.request;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hungpt.myblog.entity.Config}
 */
@Value
public class ConfigRequest implements Serializable {
    UUID id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String key;
    String value;
    String description;
    String type;
}