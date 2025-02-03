package com.hungpt.myblog.service;

import com.hungpt.myblog.entity.PostView;
import com.hungpt.myblog.repository.PostViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostViewSyncService {

    private final PostViewRepository postViewRepository;

    private static final String VIEW_COUNT_KEY_PREFIX = "post:view:";

    /**
     * Sync view counts from Redis to the database.
     */
    public void syncViewCounts() {
        LocalDate today = LocalDate.now();
        String todayPattern = VIEW_COUNT_KEY_PREFIX + "*:" + today;

        Set<String> keys = getKeysFromRedis(todayPattern);
        if (keys.isEmpty()) {
            log.info("No view counts to sync for today.");
            return;
        }

        for (String key : keys) {
            processKey(key, today);
        }
    }

    /**
     * Fetch Redis keys matching the pattern.
     *
     * @param pattern Redis key pattern.
     * @return Set of matching keys.
     */
    private Set<String> getKeysFromRedis(String pattern) {
        Set<String> keys = postViewRepository.getKeysByPattern(pattern);
        if (keys == null || keys.isEmpty()) {
            log.warn("No keys found matching the pattern: {}", pattern);
        }
        return keys;
    }

    /**
     * Process each key, extract postId and view count, and update the PostView entity.
     *
     * @param key   Redis key.
     * @param today Current date.
     */
    private void processKey(String key, LocalDate today) {
        String[] parts = key.split(":");
        if (parts.length < 3) {
            log.warn("Skipping invalid Redis key: {}", key);
            return;  // Skip invalid keys
        }

        try {
            UUID postId = UUID.fromString(parts[2]);
            long viewCount = postViewRepository.getViewCountForKey(key);

            updatePostViewCount(postId, viewCount, today);
        } catch (Exception e) {
            log.error("Error processing Redis key {}: {}", key, e.getMessage());
        }
    }

    /**
     * Update the view count for the given postId and date.
     *
     * @param postId Post ID.
     * @param count  View count to add.
     * @param date   Date for the view count.
     */
    private void updatePostViewCount(UUID postId, long count, LocalDate date) {
        try {
            PostView postView = postViewRepository.findByPostIdAndDate(postId, date)
                    .orElse(new PostView(postId, 0L));  // Create new entry if not found

            postView.incrementViewCount(count);
            postViewRepository.save(postView);

            log.info("Updated total {} views for post {} on {}", count, postId, date);

            postViewRepository.removeRedisKeyForPost(postId);  // Cleanup Redis key after syncing

        } catch (Exception e) {
            log.error("Error updating view count for post {}: {}", postId, e.getMessage());
        }
    }
}
