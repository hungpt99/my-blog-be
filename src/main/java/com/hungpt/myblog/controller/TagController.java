package com.hungpt.myblog.controller;

import com.hungpt.myblog.dto.request.TagRequest;
import com.hungpt.myblog.dto.response.TagResponse;
import com.hungpt.myblog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController extends AbstractBaseController {

    private final TagService tagService;

    /**
     * Retrieves all tags.
     *
     * @return List of TagResponse DTOs.
     */
    @GetMapping
    public List<TagResponse> getTags() {
        return tagService.getAllTags();
    }


}
