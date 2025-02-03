package com.hungpt.myblog.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisWrapper {

    private final StringRedisTemplate redisTemplate;

    /**
     * Tăng giá trị của một key trong Redis.
     *
     * @param key   Key trong Redis.
     * @return Giá trị sau khi tăng.
     */
    public long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * Đặt thời gian hết hạn cho một key trong Redis.
     *
     * @param key    Key cần đặt TTL.
     * @param time   Thời gian tồn tại.
     * @param unit   Đơn vị thời gian.
     */
    public void setExpire(String key, long time, TimeUnit unit) {
        redisTemplate.expire(key, time, unit);
    }

    /**
     * Lấy giá trị của một key từ Redis.
     *
     * @param key Key trong Redis.
     * @return Giá trị của key (có thể null).
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * Lấy danh sách tất cả các key phù hợp với pattern.
     *
     * @param pattern Biểu thức regex của key.
     * @return Tập hợp các key.
     */
    public Set<String> getKeysByPattern(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
