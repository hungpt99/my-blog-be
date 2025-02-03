package com.hungpt.myblog.entity.specification.criteria;

import com.hungpt.myblog.dto.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class PostCriteria extends PaginationCriteria {
    private String title;
    private PostStatus status;
    private UUID tagId;
    private UUID categoryId;
    private String startDate;  // Format: yyyy-MM-dd
    private String endDate;    // Format: yyyy-MM-dd
}
