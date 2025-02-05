package com.hungpt.myblog.controller;

import com.hungpt.myblog.constants.ApiConstants; // Importing the constants
import com.hungpt.myblog.dto.enums.PostStatus;
import com.hungpt.myblog.dto.request.PostRequest;
import com.hungpt.myblog.dto.response.PostResponse;
import com.hungpt.myblog.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiConstants.API_COMMON_PREFIX + ApiConstants.API_POST_PREFIX) // Using the constant for the admin prefix
@RequiredArgsConstructor
@Tag(name = "001. Post", description = "Auth API")
public class PostController extends AbstractBaseController {

    private final PostService postService;

    @PostMapping // Using the constant for creating a post
    @Operation(summary = "Create a new post", description = "Create a new post")
    @ApiResponse(responseCode = "201", description = "Post created successfully")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest post) {
        PostResponse createdPost = postService.createPost(post);
        return ResponseEntity.status(201).body(createdPost);
    }

    @GetMapping(ApiConstants.API_GET_POST) // Using the constant for fetching a specific post
    @Operation(summary = "Get post details", description = "Retrieve details of a specific post")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved post")
    public ResponseEntity<PostResponse> getPost(@PathVariable UUID id) {
        PostResponse post = postService.getPostById(id);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    @PutMapping(ApiConstants.API_UPDATE_POST) // Using the constant for updating a post
    @Operation(summary = "Update a post", description = "Update a specific post")
    @ApiResponse(responseCode = "200", description = "Post updated successfully")
    public ResponseEntity<PostResponse> updatePost(@PathVariable UUID id, @RequestBody PostRequest post) {
        PostResponse updatedPost = postService.updatePost(id, post);
        return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
    }

    @DeleteMapping(ApiConstants.API_DELETE_POST) // Using the constant for deleting a post
    @Operation(summary = "Delete a post", description = "Delete a specific post")
    @ApiResponse(responseCode = "204", description = "Post deleted successfully")
    public ResponseEntity<Void> deletePost(@PathVariable UUID id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(ApiConstants.API_GET_POSTS_BY_TAG) // Using the constant for getting posts by tag
    @Operation(summary = "Get posts by tag", description = "Retrieve a list of posts associated with a specific tag")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved posts by tag")
    public ResponseEntity<List<PostResponse>> getPostsByTag(@PathVariable UUID tagId) {
        List<PostResponse> posts = postService.getPostsByTag(tagId);
        return ResponseEntity.ok(posts);
    }

    @GetMapping(ApiConstants.API_GET_POSTS_BY_CATEGORY) // Using the constant for getting posts by category
    @Operation(summary = "Get posts by category", description = "Retrieve a list of posts associated with a specific category")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved posts by series")
    public ResponseEntity<List<PostResponse>> getPostByCategory(@PathVariable UUID seriesId) {
        List<PostResponse> posts = postService.getPostByCategory(seriesId);
        return ResponseEntity.ok(posts);
    }

    @PatchMapping(ApiConstants.API_UPDATE_POST_STATUS) // Using the constant for updating post status
    @Operation(summary = "Update post status", description = "Update the status of a specific post")
    @ApiResponse(responseCode = "200", description = "Post status updated successfully")
    public ResponseEntity<PostResponse> updatePostStatus(@PathVariable UUID id, @RequestParam PostStatus status) {
        PostResponse updatedPost = postService.updatePostStatus(id, status);
        return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
    }

    @GetMapping(ApiConstants.API_GET_POSTS_WITH_FILTERS) // Using the constant for getting filtered posts
    @Operation(summary = "Get posts with filters and pagination", description = "Retrieve posts with filters (status, tag, category, etc.) and pagination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved filtered and paginated posts")
    public ResponseEntity<Page<PostResponse>> getPostsWithFilters(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) PostStatus status,
            @RequestParam(required = false) UUID tagId,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam int page,
            @RequestParam int size) {
        Page<PostResponse> posts = postService.getPostsWithFilters(title, status, tagId, categoryId, page, size);
        return ResponseEntity.ok(posts);
    }
}
