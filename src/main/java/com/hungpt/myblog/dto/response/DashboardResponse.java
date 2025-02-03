package com.hungpt.myblog.dto.response;

import lombok.*;

import java.io.Serializable;

/**
 * DTO for returning dashboard statistics.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse extends AbstractBaseResponse implements Serializable {

    private long totalPosts;
    private long totalViews;
    private long totalComments;

}
