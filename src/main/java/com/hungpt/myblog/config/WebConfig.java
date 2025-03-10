package com.hungpt.myblog.config;

import com.hungpt.myblog.interceptor.AdminInterceptor;
import com.hungpt.myblog.interceptor.LoggingInterceptor;
import com.hungpt.myblog.interceptor.RateLimitInterceptor;
import com.hungpt.myblog.interceptor.UserInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RateLimitInterceptor rateLimitInterceptor;
    private final AdminInterceptor adminInterceptor;
    private final UserInterceptor userInterceptor;
    private final LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(rateLimitInterceptor);
        registry.addInterceptor(adminInterceptor);
        registry.addInterceptor(userInterceptor);
        registry.addInterceptor(loggingInterceptor);
    }
}
