package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.context.TenantContext;
import org.martpilot.dto.CategoryDTO;
import org.martpilot.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CategoryController - Tenant-scoped category management.
 * Tenant is resolved from subdomain, storeId from header.
 */
@RestController
@RequestMapping("/api/v1/stores/{storeId}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Create category for tenant/store.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<CategoryDTO> create(
            @PathVariable Long storeId,
            @RequestBody CategoryDTO categoryDTO) {
        Long tenantId = getTenantId();
        TenantContext.setStoreId(storeId);
        categoryDTO.setTenantId(tenantId);
        categoryDTO.setStoreId(storeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(tenantId, categoryDTO));
    }

    /**
     * Get category by ID.
     */
    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
    public ResponseEntity<CategoryDTO> getById(
            @PathVariable Long storeId,
            @PathVariable Long categoryId) {
        Long tenantId = getTenantId();
        return ResponseEntity.ok(categoryService.getById(tenantId, categoryId));
    }

    /**
     * Get all categories for store.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
    public ResponseEntity<List<CategoryDTO>> getByStoreId(@PathVariable Long storeId) {
        Long tenantId = getTenantId();
        return ResponseEntity.ok(categoryService.getByTenantId(tenantId, storeId));
    }

    /**
     * Update category.
     */
    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<CategoryDTO> update(
            @PathVariable Long storeId,
            @PathVariable Long categoryId,
            @RequestBody CategoryDTO categoryDTO) {
        Long tenantId = getTenantId();
        categoryDTO.setTenantId(tenantId);
        categoryDTO.setStoreId(storeId);
        return ResponseEntity.ok(categoryService.update(tenantId, storeId, categoryDTO));
    }

    /**
     * Delete category.
     */
    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long storeId,
            @PathVariable Long categoryId) {
        Long tenantId = getTenantId();
        categoryService.delete(tenantId, categoryId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Safety check for tenant resolution.
     */
    private Long getTenantId() {
        Long tenantId = TenantContext.getTenantId();
        if (tenantId == null) {
            throw new IllegalStateException("Tenant not resolved. Please access via correct subdomain.");
        }
        return tenantId;
    }
}