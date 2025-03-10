package com.hungpt.myblog.repository;

import com.hungpt.myblog.entity.Post;
import com.hungpt.myblog.dto.enums.PostStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {

    /**
     * Sum of all post view counts.
     */
    @Query("SELECT COALESCE(SUM(p.viewCount), 0) FROM Post p")
    long sumViewCounts();

    /**
     * Sum of all post comment counts.
     */
    @Query("SELECT COALESCE(SUM(p.commentCount), 0) FROM Post p")
    long sumCommentCounts();

    /**
     * Count the number of posts with the given status.
     */
    long countByStatus(PostStatus status);

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + :increment WHERE p.id = :postId")
    void incrementViewCount(UUID postId, long increment);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.id = :tagId")
    List<Post> findByTagId(@Param("tagId") UUID tagId);

    @Query("SELECT p FROM Post p WHERE p.category.id = :categoryId")
    List<Post> findByCategoryId(@Param("categoryId") UUID categoryId);

    /**
     * Find post by slug.
     */
    Optional<Post> findBySlug(String slug);

    /**
     * Add a tag to a post.
     */
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO post_tag (post_id, tag_id) VALUES (:postId, :tagId)", nativeQuery = true)
    void addTagToPost(@Param("postId") UUID postId, @Param("tagId") UUID tagId);
}
