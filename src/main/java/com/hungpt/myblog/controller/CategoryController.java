package com.hungpt.myblog.controller;

import com.hungpt.myblog.constants.ApiConstants;
import com.hungpt.myblog.dto.request.CategoryRequest;
import com.hungpt.myblog.dto.response.CategoryResponse;
import com.hungpt.myblog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(ApiConstants.API_COMMON_PREFIX + ApiConstants.API_CATEGORY_PREFIX) // Using the constant for category prefix
@RequiredArgsConstructor
@Tag(name = "001. Category", description = "Category API")
public class CategoryController extends AbstractBaseController {

    private final CategoryService categoryService;

    @PostMapping(ApiConstants.API_CREATE_CATEGORY)  // Use constant for create category
    @PreAuthorize("hasRole(T(com.hungpt.myblog.entity.Role).ADMIN.getRoleName())")  // Use enum in @PreAuthorize
    @Operation(summary = "Create a new category", description = "Create a new category with the provided details")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest category) {
        CategoryResponse createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping(ApiConstants.API_UPDATE_CATEGORY)  // Use constant for update category
    @PreAuthorize("hasRole(T(com.hungpt.myblog.entity.Role).ADMIN.getRoleName())")
    @Operation(summary = "Update an existing category", description = "Update the details of an existing category")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable UUID id, @RequestBody CategoryRequest category) {
        CategoryResponse updatedCategory = categoryService.updateCategory(id, category);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping(ApiConstants.API_DELETE_CATEGORY)  // Use constant for delete category
    @PreAuthorize("hasRole(T(com.hungpt.myblog.entity.Role).ADMIN.getRoleName())")
    @Operation(summary = "Delete a category", description = "Mark a category as deleted (soft delete)")
    public ResponseEntity<String> deleteCategory(@PathVariable UUID id) {
        categoryService.softDeleteCategory(id);
        return new ResponseEntity<>("Category marked as deleted successfully", HttpStatus.OK);
    }
}
