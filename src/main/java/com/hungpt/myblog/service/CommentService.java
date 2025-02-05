package com.hungpt.myblog.service;

import com.hungpt.myblog.dto.request.CommentRequest;
import com.hungpt.myblog.dto.response.CommentResponse;
import com.hungpt.myblog.entity.Comment;
import com.hungpt.myblog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public List<CommentResponse> getCommentsByPostId(UUID postId) {
        List<Comment> comments = commentRepository.findByPostId(postId); // Assumes a `findByPostId` method in `CommentRepository`
        return comments.stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors .toList());
    }

    public CommentResponse createComment(CommentRequest commentRequest) {
        Comment comment = new Comment();
        // Map fields from commentRequest to comment entity
        comment.setContent(commentRequest.getContent());
        // Set other fields like postId, createdBy, etc.
        commentRepository.save(comment);
        return CommentResponse.fromEntity(comment);
    }


    public void deleteComment(UUID commentId) {
        commentRepository.deleteById(commentId);
    }
}
