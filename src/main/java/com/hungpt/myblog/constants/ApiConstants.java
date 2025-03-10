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
    public static final String API_POST_PREFIX = "/posts";
    public static final String API_CREATE_POST = "/";
    public static final String API_GET_POST = "/{id}";
    public static final String API_UPDATE_POST = "/{id}";
    public static final String API_DELETE_POST = "/{id}";
    public static final String API_GET_POSTS_BY_TAG = "/tag/{tagId}";
    public static final String API_GET_POSTS_BY_CATEGORY = "/category/{seriesId}";
    public static final String API_UPDATE_POST_STATUS = "/{id}/status";
    public static final String API_GET_POSTS_WITH_FILTERS = "/filter";
    public static final String API_INCREMENT_POST_VIEW = "/{id}/view";
    public static final String API_GET_TAGS_FOR_POST = "/{postId}/tags";
    public static final String API_CREATE_POST_TAG_RELATION = "/{postId}/tags";
    public static final String API_GET_POST_BY_ID = "/{id}";
    public static final String API_GET_POST_BY_SLUG = "/slug/{slug}";
    public static final String API_GET_FILTERED_POSTS = "/filtered";
    public static final String API_GET_PAGINATED_POSTS = "/paginated";

    /**
     * DASHBOARD API
     */
    public static final String API_DASHBOARD_PREFIX = "/posts";
    public static final String API_DASHBOARD_OVERVIEW = "/overview";

    /**
     * CATEGORY API constants
     */
    public static final String API_CATEGORY_PREFIX = "/categories";
    public static final String API_CREATE_CATEGORY = "/";
    public static final String API_UPDATE_CATEGORY = "/{id}";
    public static final String API_DELETE_CATEGORY = "/{id}";

    /**
     * COMMENT API constants
     */
    public static final String API_COMMENT_PREFIX = "/comments";
    public static final String API_GET_COMMENTS = "/{postId}";
    public static final String API_CREATE_COMMENT = "/";
    public static final String API_DELETE_COMMENT = "/{commentId}";

    /**
     * TAG API constants
     */
    public static final String API_TAG_PREFIX = "/tags";
    public static final String API_GET_ALL_TAGS = "/all";
    public static final String API_GET_RELATED_TAGS = "/posts/{postId}/tags";
    public static final String API_CREATE_TAGS = "/";
    public static final String API_DELETE_TAG = "/{tagId}";
}
