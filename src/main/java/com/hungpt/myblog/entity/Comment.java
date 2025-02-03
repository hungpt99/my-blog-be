package com.hungpt.myblog.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a Comment.
 */
@Entity
@Table(name = "comments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AbstractBaseEntity {

    // The content of the comment
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // Many comments can belong to a single post
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // Many comments can be made by a single user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
