package com.hungpt.myblog.entity;

import com.hungpt.myblog.dto.enums.PostStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends AbstractBaseEntity {

    // The title of the post
    @Column(nullable = false, length = 255)
    private String title;

    // The content of the post
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // The slug for SEO-friendly URLs
    @Column(nullable = false, unique = true, length = 255)
    private String slug;

    // The status of the post (e.g., draft, published)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PostStatus status;

    // Total view count for the post
    @Column(nullable = false)
    private long viewCount;

    // Total comment count for the post
    @Column(nullable = false)
    private long commentCount;

    // Relationship with Category (Many-to-One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Relationship with Tags (Many-to-Many)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    // Soft delete support
    @Column(nullable = false)
    private boolean deleted = false;
}
