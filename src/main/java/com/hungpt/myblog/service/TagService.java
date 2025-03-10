package com.hungpt.myblog.service;

import com.hungpt.myblog.dto.response.TagResponse;
import com.hungpt.myblog.entity.Tag;
import com.hungpt.myblog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public List<TagResponse> getTags(int page, int size) {
        return tagRepository.findAll().stream()
                .skip((long) (page - 1) * size)
                .limit(size)
                .map(TagResponse::fromEntity)
                .collect(Collectors.toList());
    }

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

    @Transactional
    public List<TagResponse> addTags(List<String> newTags) {
        List<Tag> tags = newTags.stream()
                .map(name -> {
                    Tag tag = new Tag();
                    tag.setName(name);
                    return tag;
                })
                .collect(Collectors.toList());
        return tagRepository.saveAll(tags).stream()
                .map(TagResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public TagResponse updateTag(UUID id, TagResponse tagRequest) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tag.setName(tagRequest.getName());
        tag = tagRepository.save(tag);
        return TagResponse.fromEntity(tag);
    }

    public List<TagResponse> getTagsByPost(UUID postId) {
        List<Tag> tags = tagRepository.findByPostId(postId);
        return tags.stream()
                .map(TagResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public void deleteTag(UUID id, String deletedBy) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tag.softDelete(deletedBy);
        tagRepository.save(tag);
    }

    public TagResponse findById(UUID id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        return TagResponse.fromEntity(tag);
    }

    public List<TagResponse> findTagsByPostId(UUID postId) {
        List<Tag> tags = tagRepository.findByPostId(postId);
        return tags.stream()
                .map(TagResponse::fromEntity)
                .collect(Collectors.toList());
    }

}
