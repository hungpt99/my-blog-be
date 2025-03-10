package com.hungpt.myblog.controller;

import com.hungpt.myblog.constants.ApiConstants;
import com.hungpt.myblog.dto.response.TagResponse;
import com.hungpt.myblog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiConstants.API_COMMON_PREFIX + ApiConstants.API_TAG_PREFIX)
@RequiredArgsConstructor
@Tag(name = "002. Tag", description = "Tag API")
public class TagController extends AbstractBaseController {

    private final TagService tagService;

    @GetMapping
    @Operation(summary = "Get all tags with pagination", description = "Retrieve a paginated list of tags")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved tags")
    public ResponseEntity<List<TagResponse>> getTags(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(tagService.getTags(page, size));
    }

    @GetMapping(ApiConstants.API_GET_ALL_TAGS)
    @Operation(summary = "Get all tags", description = "Retrieve all tags without pagination")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all tags")
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @PostMapping(ApiConstants.API_CREATE_TAGS)
    @Operation(summary = "Create new tags", description = "Add multiple new tags")
    @ApiResponse(responseCode = "201", description = "Tags created successfully")
    public ResponseEntity<List<TagResponse>> addTags(@RequestBody List<String> newTags) {
        return ResponseEntity.status(201).body(tagService.addTags(newTags));
    }

    @GetMapping(ApiConstants.API_GET_RELATED_TAGS)
    @Operation(summary = "Get related tags for a post", description = "Retrieve all tags associated with a specific post")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved related tags")
    public ResponseEntity<List<TagResponse>> getRelatedTags(@PathVariable UUID postId) {
        return ResponseEntity.ok(tagService.getTagsByPost(postId));
    }

    @DeleteMapping(ApiConstants.API_DELETE_TAG)
    @Operation(summary = "Delete a tag", description = "Remove a tag by its ID")
    @ApiResponse(responseCode = "204", description = "Tag deleted successfully")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID tagId, Authentication authentication) {
        String deletedBy = authentication.getName();
        tagService.deleteTag(tagId, deletedBy);
        return ResponseEntity.noContent().build();
    }
}

