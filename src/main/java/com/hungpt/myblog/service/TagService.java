package com.hungpt.myblog.service;

import com.hungpt.myblog.dto.response.TagResponse;
import com.hungpt.myblog.entity.Tag;
import com.hungpt.myblog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(TagResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public TagResponse getTagById(UUID id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        return TagResponse.fromEntity(tag);
    }

    public TagResponse createTag(Tag request) {
        Tag tag = new Tag();
        tag.setName(request.getName());
        tag = tagRepository.save(tag);
        return TagResponse.fromEntity(tag);
    }

    public TagResponse updateTag(UUID id, Tag request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tag.setName(request.getName());
        tag = tagRepository.save(tag);
        return TagResponse.fromEntity(tag);
    }

    public void deleteTag(UUID id) {
        if (!tagRepository.existsById(id)) {
            throw new RuntimeException("Tag not found");
        }
        tagRepository.deleteById(id);
    }
}
