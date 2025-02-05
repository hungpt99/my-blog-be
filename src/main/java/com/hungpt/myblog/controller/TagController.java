package com.hungpt.myblog.controller;

import com.hungpt.myblog.constants.ApiConstants;
import com.hungpt.myblog.dto.response.TagResponse;
import com.hungpt.myblog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.API_COMMON_PREFIX + ApiConstants.API_TAG_PREFIX) // Using the constant for the admin prefix
@RequiredArgsConstructor
@Tag(name = "001. Post", description = "Auth API")
public class TagController extends AbstractBaseController {

    private final TagService tagService;

    /**
     * Retrieves all tags.
     *
     * @return List of TagResponse DTOs.
     */
    @GetMapping
    @Operation(summary = "Get posts by tag", description = "Retrieve a list of posts associated with a specific tag")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved posts by tag")
    public List<TagResponse> getTags() {
        return tagService.getAllTags();
    }


}
