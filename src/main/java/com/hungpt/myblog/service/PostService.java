package com.hungpt.myblog.service;

import com.hungpt.myblog.dto.enums.PostStatus;
import com.hungpt.myblog.dto.request.PostRequest;
import com.hungpt.myblog.dto.response.PostResponse;
import com.hungpt.myblog.entity.Post;
import com.hungpt.myblog.entity.specification.PostSpecification;
import com.hungpt.myblog.entity.specification.criteria.PostCriteria;
import com.hungpt.myblog.exception.NotFoundException;
import com.hungpt.myblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostViewService postViewService;

    // Create a new post
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

    // Update an existing post
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

    // Get post by ID
    public PostResponse getPostById(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post not found"));

        // Increment view count
        postViewService.incrementViewCount(postId.toString());

        return mapToResponse(post);
    }

    // Get all posts
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Delete a post
    @Transactional
    public void deletePost(UUID postId) {
        postRepository.deleteById(postId);
    }

    // Validate PostStatus to avoid IllegalArgumentException
    private PostStatus validatePostStatus(String status) {
        try {
            return PostStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid post status: " + status);
        }
    }

    // Map Post to PostResponse
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

    public Page<PostResponse> getPostsWithFilters(String title, PostStatus status, UUID tagId, UUID categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Create a PostCriteria object from the method parameters
        PostCriteria criteria = new PostCriteria();
        criteria.setTitle(title);
        criteria.setStatus(status);
        criteria.setTagId(tagId);
        criteria.setCategoryId(categoryId);

        // Create the PostSpecification using the criteria
        PostSpecification postSpecification = new PostSpecification(criteria);

        // Get the filtered posts from the repository
        Page<Post> postsPage = postRepository.findAll(postSpecification, pageable);

        // Map the Post entities to PostResponse and return the page
        return postsPage.map(this::mapToResponse);
    }
}
