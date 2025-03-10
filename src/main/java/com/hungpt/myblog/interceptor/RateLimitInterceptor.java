package com.hungpt.myblog.interceptor;

import com.hungpt.myblog.annotation.RateLimited;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private final ConcurrentMap<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket createNewBucket(int requests, int duration) {
        return Bucket4j.builder()
                .addLimit(Bandwidth.classic(requests, Refill.greedy(requests, Duration.ofMinutes(duration))))
                .build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true; // Skip if not a controller method
        }

        Method method = handlerMethod.getMethod();
        RateLimited rateLimited = method.getAnnotation(RateLimited.class);
        if (rateLimited == null) {
            return true; // Skip if the annotation is not present
        }

        String clientIP = request.getRemoteAddr();
        String key = clientIP + ":" + method.getName(); // Unique key per user & method
        Bucket bucket = cache.computeIfAbsent(key, k -> createNewBucket(rateLimited.requests(), rateLimited.duration()));

        if (!bucket.tryConsume(1)) {
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            response.getWriter().write("Too many requests. Try again later.");
            log.warn("Rate limit exceeded for IP: {} on method: {}", clientIP, method.getName());
            return false;
        }
        return true;
    }
}
