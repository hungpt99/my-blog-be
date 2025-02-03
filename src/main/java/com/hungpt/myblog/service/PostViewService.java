package com.hungpt.myblog.service;

import com.hungpt.myblog.redis.RedisWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class PostViewService {

    private final RedisWrapper redisWrapper;

    private static final String VIEW_COUNT_KEY_PREFIX = "post:view:";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Tăng số lượt xem cho một bài viết.
     *
     * @param postId ID của bài viết.
     */
    public void incrementViewCount(String postId) {
        String todayKey = getTodayKey(postId);
        redisWrapper.increment(todayKey);
        redisWrapper.setExpire(todayKey, 7, TimeUnit.DAYS); // Lưu trữ trong 7 ngày
    }

    /**
     * Lấy số lượt xem của một bài viết trong ngày hôm nay.
     *
     * @param postId ID của bài viết.
     * @return Số lượt xem hôm nay.
     */
    public long getViewCountForToday(String postId) {
        String todayKey = getTodayKey(postId);
        String viewCount = redisWrapper.get(todayKey);
        return (viewCount != null) ? Long.parseLong(viewCount) : 0;
    }

    /**
     * Lấy tổng số lượt xem của tất cả bài viết trong ngày hôm nay.
     *
     * @return Tổng số lượt xem hôm nay.
     */
    public long getTotalViewsForToday() {
        String todayDate = getTodayDate();
        Set<String> keys = redisWrapper.getKeysByPattern(VIEW_COUNT_KEY_PREFIX + "*:" + todayDate);

        if (keys == null || keys.isEmpty()) {
            return 0;
        }

        return keys.stream()
                .mapToLong(key -> {
                    String count = redisWrapper.get(key);
                    return (count != null) ? Long.parseLong(count) : 0;
                })
                .sum();
    }

    private String getTodayKey(String postId) {
        return VIEW_COUNT_KEY_PREFIX + postId + ":" + getTodayDate();
    }

    private String getTodayDate() {
        return LocalDate.now().format(DATE_FORMATTER);
    }
}
