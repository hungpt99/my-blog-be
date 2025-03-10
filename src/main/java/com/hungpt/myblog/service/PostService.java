package com.hungpt.myblog.service;

import com.hungpt.myblog.dto.enums.PostStatus;
import com.hungpt.myblog.dto.request.PostRequest;
import com.hungpt.myblog.dto.response.PostResponse;
import com.hungpt.myblog.dto.response.TagResponse;
import com.hungpt.myblog.entity.Post;
import com.hungpt.myblog.entity.specification.PostSpecification;
import com.hungpt.myblog.entity.specification.criteria.PostCriteria;
import com.hungpt.myblog.exception.NotFoundException;
import com.hungpt.myblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostViewService postViewService;
    private final TagService tagService;
    private final Map<UUID, Integer> postViewCache = new ConcurrentHashMap<>();

    @Transactional
    public PostResponse createPost(PostRequest request) {
        PostStatus postStatus = validatePostStatus(String.valueOf(request.getStatus()));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .status(postStatus)
                .viewCount(0)
                .commentCount(0)
                .build();

        Post savedPost = postRepository.save(post);
        return mapToResponse(savedPost);
    }

    @Transactional
    public PostResponse updatePost(UUID postId, PostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setStatus(validatePostStatus(String.valueOf(request.getStatus())));

        Post updatedPost = postRepository.save(post);
        return mapToResponse(updatedPost);
    }

    public PostResponse getPostById(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        postViewService.incrementViewCount(postId.toString());
        return mapToResponse(post);
    }

    public PostResponse getPostBySlug(String slug) {
        Post post = postRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Post not found"));
        return mapToResponse(post);
    }

    public Page<PostResponse> getPaginatedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postRepository.findAll(pageable);
        return postsPage.map(this::mapToResponse);
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapToResponse).toList();
    }

    @Transactional
    public void deletePost(UUID postId) {
        postRepository.deleteById(postId);
    }

    private PostStatus validatePostStatus(String status) {
        try {
            return PostStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid post status: " + status);
        }
    }

    private PostResponse mapToResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .status(post.getStatus())
                .viewCount(postViewService.getViewCountForToday(post.getId().toString()))
                .commentCount(post.getCommentCount())
                .build();
    }

    public PostResponse updatePostStatus(UUID id, PostStatus status) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post not found"));
        post.setStatus(status);
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        return mapToResponse(post);
    }

    public List<PostResponse> getPostsByTag(UUID tagId) {
        List<Post> posts = postRepository.findByTagId(tagId);
        return posts.stream().map(this::mapToResponse).toList();
    }

    public List<PostResponse> getPostByCategory(UUID categoryId) {
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        return posts.stream().map(this::mapToResponse).toList();
    }

    public Page<PostResponse> getPostsWithFilters(String title, PostStatus status, UUID tagId, UUID categoryId, String fromDate, String toDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        PostCriteria criteria = new PostCriteria(title, status, tagId, categoryId, fromDate, toDate);
        PostSpecification postSpecification = new PostSpecification(criteria);
        Page<Post> postsPage = postRepository.findAll(postSpecification, pageable);
        return postsPage.map(this::mapToResponse);
    }

    @Transactional
    public void createPostTagRelation(UUID postId, List<UUID> tagIds) {
        getPostById(postId);

        for (UUID tagId : tagIds) {
            tagService.findById(tagId);

            postRepository.addTagToPost(postId, tagId);
        }
    }


    public List<TagResponse> getTagsForPost(UUID postId) {
        return tagService.findTagsByPostId(postId);
    }

    public void incrementPostViewCount(UUID postId) {
        postViewCache.merge(postId, 1, Integer::sum);
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // 5 minutes
    @Transactional
    public void updatePostViewCounts() {
        if (postViewCache.isEmpty()) {
            return;
        }

        postViewCache.forEach((postId, count) -> {
            postRepository.findById(postId).ifPresent(post -> {
                post.setViewCount(post.getViewCount() + count);
                postRepository.save(post);
            });
        });

        // Clear the cache after persisting
        postViewCache.clear();
    }
}
