package com.hungpt.myblog.dto.response;

import com.hungpt.myblog.entity.Category;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link com.hungpt.myblog.entity.Category}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse extends AbstractBaseResponse implements Serializable, IResponse<CategoryResponse, Category> {

    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String name;
    private String description;

    // Implement the fromEntity method to convert from entity to DTO
    @Override
    public CategoryResponse fromEntity(Category entity) {

        return CategoryResponse.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
