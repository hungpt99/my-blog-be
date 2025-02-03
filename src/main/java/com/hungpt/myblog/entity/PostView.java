package com.hungpt.myblog.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("PostView")
public class PostView implements Serializable {


    @Id
    private UUID postId;
    private long viewCount = 0L;  // Default viewCount to 0

    /**
     * Increment the view count.
     *
     * @param count Number of views to add.
     */
    public void incrementViewCount(long count) {
        this.viewCount += count;
    }
}
