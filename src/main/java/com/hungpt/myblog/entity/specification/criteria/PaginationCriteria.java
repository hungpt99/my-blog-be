package com.hungpt.myblog.entity.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PaginationCriteria {
    private Integer page;

    private Integer size;

    private String sortBy;

    private String sort;

    private String[] columns;
}
