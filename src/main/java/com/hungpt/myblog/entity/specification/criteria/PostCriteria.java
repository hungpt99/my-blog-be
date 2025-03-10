package com.hungpt.myblog.entity.specification.criteria;

import com.hungpt.myblog.dto.enums.PostStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class PostCriteria extends PaginationCriteria {
    String title;
    PostStatus status;
    UUID tagId;
    UUID categoryId;
    String startDate;  // Format: yyyy-MM-dd
    String endDate;    // Format: yyyy-MM-dd
}
