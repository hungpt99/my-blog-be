package com.hungpt.myblog.repository;

import com.hungpt.myblog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    // Optional: Find category by name (or any other fields)
    Optional<Category> findByName(String name);

    // Optional: Find categories by a part of the name or description (e.g., for search functionality)
    List<Category> findByNameContainingIgnoreCase(String namePart);

    // Optional: Find categories by description (if you want filtering by description)
    List<Category> findByDescriptionContainingIgnoreCase(String descriptionPart);
}
