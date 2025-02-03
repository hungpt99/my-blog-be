package com.hungpt.myblog.repository;

import com.hungpt.myblog.entity.PostView;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PostViewRepository extends CrudRepository<PostView, UUID> {

    // Find PostView by postId and date
    Optional<PostView> findByPostIdAndDate(UUID postId, LocalDate date);

    // Method to increment the view count in Redis
    PostView incrementViewCount(UUID postId, long count);

    // Custom method to get Redis keys by pattern (using RedisTemplate or RedisCommands)
    Set<String> getKeysByPattern(String pattern);

    // Custom method to get the view count for a specific Redis key
    long getViewCountForKey(String key);

    // Method to remove Redis key for the given postId (cleanup after syncing)
    void removeRedisKeyForPost(UUID postId);
}
