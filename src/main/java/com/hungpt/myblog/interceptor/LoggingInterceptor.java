package com.hungpt.myblog.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final String START_TIME = "startTime";
    private static final String REQUEST_ID = "requestId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = request.getHeader("X-Request-ID");
        if (requestId == null || requestId.isEmpty()) {
            requestId = UUID.randomUUID().toString(); // Generate if not provided
        }

        request.setAttribute(START_TIME, System.currentTimeMillis());
        request.setAttribute(REQUEST_ID, requestId);
        response.setHeader("X-Request-ID", requestId); // Set in response for tracking

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long duration = System.currentTimeMillis() - (long) request.getAttribute(START_TIME);
        String requestId = (String) request.getAttribute(REQUEST_ID);

        logger.info("[{}] {} {} | Status: {} | Time: {} ms | IP: {} | Request ID: {} {}",
                request.getMethod(), request.getRequestURI(), request.getProtocol(),
                response.getStatus(), duration, request.getRemoteAddr(), requestId,
                (ex != null ? "| Exception: " + ex.getMessage() : ""));
    }
}
