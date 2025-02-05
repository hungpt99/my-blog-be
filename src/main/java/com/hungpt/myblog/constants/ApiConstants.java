package com.hungpt.myblog.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiConstants {

    // API Version constant
    public static final String API_VERSION = "1.0.0";

    /**
     * PREFIX
     */
    public static final String API_ADMIN_PREFIX = "/api/admin";
    public static final String API_COMMON_PREFIX = "/api/common";
    /**
     * API ADMIN AUTH
     */
    public static final String API_AUTH_PREFIX = "/auth";
    public static final String API_ADMIN_LOGIN = "/login";
    public static final String API_ADMIN_REFRESH_TOKEN = "/refresh";
    public static final String API_ADMIN_VERIFY_EMAIL = "/email-verification/{token}";
    public static final String API_LOGOUT = "/logout";
    /**
     * API ADMIN CONFIG
     */
    public static final String API_CONFIG_PREFIX = "/api/config";
    /**
     * API POST constants
     */
    public static final String API_POST_PREFIX = "/posts"; // Post-related prefix

    public static final String API_GET_POST = "/{id}"; // Get specific post
    public static final String API_UPDATE_POST = "/{id}"; // Update post
    public static final String API_DELETE_POST = "/{id}"; // Delete post
    public static final String API_GET_POSTS_BY_TAG = "/tag/{tagId}"; // Get posts by tag
    public static final String API_GET_POSTS_BY_CATEGORY = "/category/{seriesId}"; // Get posts by category
    public static final String API_UPDATE_POST_STATUS = "/{id}/status"; // Update post status
    public static final String API_GET_POSTS_WITH_FILTERS = "/filter"; // Get posts with filters and pagination

    /**
     * DASHBOARD API
     */
    public static final String API_DASHBOARD_PREFIX = "/posts";
    public static final String API_DASHBOARD_OVERVIEW = "/overview";

    /**
     * CATEGORY
     */
    public static final String API_CATEGORY_PREFIX = "/categories";
    public static final String API_CREATE_CATEGORY = "/";
    public static final String API_UPDATE_CATEGORY = "/{id}";
    public static final String API_DELETE_CATEGORY = "/{id}";
    /**
     * COMMENT API constants
     */
    public static final String API_GET_COMMENTS = "/{postId}";
    public static final String API_CREATE_COMMENT = "/";
    public static final String API_DELETE_COMMENT = "/{commentId}";
    /**
     * TAG API constants
     */
    public static final String API_TAG_PREFIX = "/";
}
