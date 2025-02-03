package com.hungpt.myblog.service;

import com.hungpt.myblog.dto.response.DashboardResponse;
import com.hungpt.myblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final PostRepository postRepository; // Repository for post data
    private final PostViewService redisPostViewService; // Redis service for post view data

    public DashboardResponse getDashboardStatistics() {
        // Fetching total posts count from the repository
        long totalPosts = getTotalPostsCount();

        // Fetching total views count for all posts from Redis
        long totalViews = getTotalViewsCount();

        // Fetching total comments count for all posts
        long totalComments = getTotalCommentsCount();

        // Returning the statistics using the builder pattern
        return DashboardResponse.builder()
                .totalPosts(totalPosts)
                .totalViews(totalViews)
                .totalComments(totalComments)
                .build();
    }

    private long getTotalPostsCount() {
        return postRepository.count(); // Optimized count query
    }

    private long getTotalViewsCount() {
        return postRepository.sumViewCounts(); // Custom query in the repository to sum view counts
    }

    private long getTotalCommentsCount() {
        return postRepository.sumCommentCounts(); // Custom query in the repository to sum comment counts
    }

}
