package com.hungpt.myblog.controller;

import com.hungpt.myblog.constants.ApiConstants;
import com.hungpt.myblog.dto.request.CategoryRequest;
import com.hungpt.myblog.dto.response.CategoryResponse;
import com.hungpt.myblog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiConstants.API_ADMIN_PREFIX + "/categories")
public class CategoryController extends AbstractBaseController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    @PreAuthorize("hasRole(T(com.hungpt.myblog.entity.Role).ADMIN.getRoleName())")  // Use enum in @PreAuthorize
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest category) {
        CategoryResponse createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(T(com.hungpt.myblog.entity.Role).ADMIN.getRoleName())")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable UUID id, @RequestBody CategoryRequest category) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.hungpt.myblog.entity.Role).ADMIN.getRoleName())")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID id) {
        categoryService.softDeleteCategory(id);
        return new ResponseEntity<>("Category marked as deleted successfully", HttpStatus.OK);
    }
}
