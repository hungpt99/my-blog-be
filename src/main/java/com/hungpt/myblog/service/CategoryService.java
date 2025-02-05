package com.hungpt.myblog.service;

import com.hungpt.myblog.dto.request.CategoryRequest;
import com.hungpt.myblog.dto.response.CategoryResponse;
import com.hungpt.myblog.entity.Category;
import com.hungpt.myblog.repository.CategoryRepository;
import com.hungpt.myblog.config.AuditorAwareImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final AuditorAwareImpl auditorAware;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, AuditorAwareImpl auditorAware) {
        this.categoryRepository = categoryRepository;
        this.auditorAware = auditorAware;
    }

    // Create category and return a CategoryResponse
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        // Set any other properties here

        category = categoryRepository.save(category);
        return CategoryResponse.fromEntity(category);
    }

    // Get all categories and return a list of CategoryResponse
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(CategoryResponse::fromEntity).toList();
    }

    // Get a category by ID and return a CategoryResponse
    public CategoryResponse getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return CategoryResponse.fromEntity(category);
    }

    // Update a category and return the updated CategoryResponse
    public CategoryResponse updateCategory(UUID id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());
        // Set other fields for updating

        category = categoryRepository.save(category);
        return CategoryResponse.fromEntity(category);
    }

    // Soft delete a category and return void (category is updated in the database)
    public void softDeleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Get the current authenticated user to set 'deletedBy'
        String currentUser = auditorAware.getCurrentAuditor().orElse("unknown");

        // Perform soft delete by setting deletedAt, deletedBy
        category.setDeletedAt(LocalDateTime.now());
        category.setDeletedBy(currentUser);

        // Save the updated category
        categoryRepository.save(category);
    }

}
