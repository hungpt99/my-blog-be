package com.hungpt.myblog.controller;

import com.hungpt.myblog.constants.ApiConstants;
import com.hungpt.myblog.dto.request.CommentRequest;
import com.hungpt.myblog.dto.response.CommentResponse;
import com.hungpt.myblog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiConstants.API_COMMON_PREFIX + ApiConstants.API_POST_PREFIX) // Using the constant for the post prefix
@RequiredArgsConstructor
@Tag(name = "001. Comment", description = "Comment API")
public class CommentController extends AbstractBaseController {

    private final CommentService commentService;

    // Get all comments for a post
    @GetMapping(ApiConstants.API_GET_COMMENTS) // Using constant for get comments by post ID
    @Operation(summary = "Get all comments for a post", description = "Retrieve all comments associated with a given post by its ID")
    public List<CommentResponse> getAllComments(@PathVariable UUID postId) {
        return commentService.getCommentsByPostId(postId);
    }

    // Create a new comment
    @PostMapping(ApiConstants.API_CREATE_COMMENT) // Using constant for create comment
    @Operation(summary = "Create a new comment", description = "Create a new comment on a specific post")
    public CommentResponse createComment(@RequestBody CommentRequest comment) {
        return commentService.createComment(comment);
    }

    // Delete a comment
    @DeleteMapping(ApiConstants.API_DELETE_COMMENT) // Using constant for delete comment
    @Operation(summary = "Delete a comment", description = "Delete a specific comment by its ID")
    public void deleteComment(@PathVariable UUID commentId) {
        commentService.deleteComment(commentId);
    }
}
