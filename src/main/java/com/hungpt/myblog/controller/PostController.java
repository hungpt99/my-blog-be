package com.hungpt.myblog.controller;

import com.hungpt.myblog.annotation.RateLimited;
import com.hungpt.myblog.constants.ApiConstants;
import com.hungpt.myblog.dto.enums.PostStatus;
import com.hungpt.myblog.dto.request.PostRequest;
import com.hungpt.myblog.dto.response.PostResponse;
import com.hungpt.myblog.dto.response.TagResponse;
import com.hungpt.myblog.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiConstants.API_COMMON_PREFIX + ApiConstants.API_POST_PREFIX)
@RequiredArgsConstructor
@Tag(name = "001. Post", description = "Post API")
public class PostController extends AbstractBaseController {

    private final PostService postService;

    @PostMapping(ApiConstants.API_CREATE_POST)
    @Operation(summary = "Create a new post", description = "Create a new post")
    @ApiResponse(responseCode = "201", description = "Post created successfully")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest post) {
        PostResponse createdPost = postService.createPost(post);
        return ResponseEntity.status(201).body(createdPost);
    }

    @GetMapping(ApiConstants.API_GET_PAGINATED_POSTS)
    @Operation(summary = "Get paginated posts", description = "Retrieve paginated list of posts")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved posts")
    public ResponseEntity<Page<PostResponse>> getPaginatedPosts(
            @RequestParam int page, @RequestParam int size) {
        Page<PostResponse> posts = postService.getPaginatedPosts(page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping(ApiConstants.API_GET_FILTERED_POSTS)
    @Operation(summary = "Get posts with filters", description = "Retrieve posts based on title, status, tag, or category")
    public ResponseEntity<Page<PostResponse>> getFilteredPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) PostStatus status,
            @RequestParam(required = false) UUID tagId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam int page,
            @RequestParam int size) {
        Page<PostResponse> posts = postService.getPostsWithFilters(title, status, tagId, categoryId, fromDate, toDate, page, size);
        return ResponseEntity.ok(posts);
    }

    @GetMapping(ApiConstants.API_GET_POST_BY_SLUG)
    @Operation(summary = "Get post by slug", description = "Retrieve post details by slug")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved post")
    public ResponseEntity<PostResponse> getPostBySlug(@PathVariable String slug) {
        PostResponse post = postService.getPostBySlug(slug);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    @GetMapping(ApiConstants.API_GET_POST_BY_ID)
    @Operation(summary = "Get post by ID", description = "Retrieve post details by ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved post")
    public ResponseEntity<PostResponse> getPost(@PathVariable UUID postId) {
        PostResponse post = postService.getPostById(postId);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    @PutMapping(ApiConstants.API_UPDATE_POST)
    @Operation(summary = "Update a post", description = "Update a specific post")
    @ApiResponse(responseCode = "200", description = "Post updated successfully")
    public ResponseEntity<PostResponse> updatePost(@PathVariable UUID id, @RequestBody PostRequest post) {
        PostResponse updatedPost = postService.updatePost(id, post);
        return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
    }

    @DeleteMapping(ApiConstants.API_DELETE_POST)
    @Operation(summary = "Delete a post", description = "Delete a specific post")
    @ApiResponse(responseCode = "204", description = "Post deleted successfully")
    public ResponseEntity<Void> deletePost(@PathVariable UUID postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(ApiConstants.API_CREATE_POST_TAG_RELATION)
    @Operation(summary = "Create relation between post and tag", description = "Associate a tag with a post")
    @ApiResponse(responseCode = "201", description = "Tag associated with post successfully")
    public ResponseEntity<Void> createPostTagRelation(@RequestParam UUID postId, @RequestParam ArrayList<UUID> tagIds) {
        postService.createPostTagRelation(postId, tagIds);
        return ResponseEntity.status(201).build();
    }

    @GetMapping(ApiConstants.API_GET_TAGS_FOR_POST)
    @Operation(summary = "Fetch all tags for a post", description = "Retrieve tags associated with a specific post")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved tags")
    public ResponseEntity<List<TagResponse>> getTagsForPost(@PathVariable UUID postId) {
        List<TagResponse> tags = postService.getTagsForPost(postId);
        return ResponseEntity.ok(tags);
    }

    @RateLimited
    @PatchMapping(ApiConstants.API_INCREMENT_POST_VIEW)
    @Operation(summary = "Increment post view count", description = "Increase the view count of a post")
    public ResponseEntity<Void> incrementPostViewCount(@PathVariable UUID id) {
        postService.incrementPostViewCount(id);
        return ResponseEntity.ok().build();
    }
}
