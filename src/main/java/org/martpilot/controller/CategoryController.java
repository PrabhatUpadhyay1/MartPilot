package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.CategoryDTO;
import org.martpilot.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants/{tenantId}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> create(
            @PathVariable Long tenantId,
            @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(tenantId, categoryDTO));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getById(
            @PathVariable Long tenantId,
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getById(tenantId, categoryId));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getByTenantId(@PathVariable Long tenantId) {
        return ResponseEntity.ok(categoryService.getByTenantId(tenantId));
    }

    @GetMapping("/root")
    public ResponseEntity<List<CategoryDTO>> getRootCategories(@PathVariable Long tenantId) {
        return ResponseEntity.ok(categoryService.getRootCategoriesByTenantId(tenantId));
    }

    @GetMapping("/{categoryId}/sub-categories")
    public ResponseEntity<List<CategoryDTO>> getSubCategories(@PathVariable Long categoryId) {
        return ResponseEntity.ok(categoryService.getSubCategoriesByParentId(categoryId));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> update(
            @PathVariable Long tenantId,
            @PathVariable Long categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.update(tenantId, categoryId, categoryDTO));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long tenantId,
            @PathVariable Long categoryId) {
        categoryService.delete(tenantId, categoryId);
        return ResponseEntity.noContent().build();
    }
}

