package com.hungpt.myblog.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * DTO for returning dashboard statistics.
 */
@Getter
@Setter
@SuperBuilder
public class DashboardResponse extends AbstractBaseResponse implements Serializable {

    private long totalPosts;
    private long totalViews;
    private long totalComments;

}
