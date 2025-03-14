package com.hungpt.myblog.repository;

import com.hungpt.myblog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("SELECT t FROM Tag t JOIN t.posts p WHERE p.id = :postId")
    List<Tag> findByPostId(@Param("postId") UUID postId);
}
