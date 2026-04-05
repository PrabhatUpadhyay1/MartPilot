package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.context.TenantContext;
import org.martpilot.dto.ProductDTO;
import org.martpilot.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.martpilot.context.TenantContext.getTenantId;

/**
 * ProductController - Tenant-scoped product management.
 * Tenant is resolved from subdomain, storeId from URL path.
 */
@RestController
@RequestMapping("/api/v1/categories/{categoryId}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Create product for tenant/store.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<ProductDTO> create(
            @PathVariable Long categoryId,
            @RequestBody ProductDTO productDTO) {
        Long tenantId = getTenantId();
        productDTO.setTenantId(tenantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(categoryId, productDTO));
    }

//    /**
//     * Get product by ID.
//     */
//    @GetMapping("/{productId}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
//    public ResponseEntity<ProductDTO> getById(
//            @PathVariable Long storeId,
//            @PathVariable Long productId) {
//        Long tenantId = getTenantId();
//        return ResponseEntity.ok(productService.getById(tenantId, productId));
//    }
//
//    /**
//     * Get all products for store.
//     */
//    @GetMapping
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
//    public ResponseEntity<List<ProductDTO>> getByStoreId(@PathVariable Long storeId) {
//        Long tenantId = getTenantId();
//        return ResponseEntity.ok(productService.getByTenantId(tenantId, storeId));
//    }
//
    /**
     * Get products by category.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
    public ResponseEntity<List<ProductDTO>> getByCategory(
            @PathVariable Long storeId,
            @PathVariable Long categoryId) {
        Long tenantId = getTenantId();
        return ResponseEntity.ok(productService.getByTenantIdAndCategoryId(tenantId, categoryId));
    }
//
//    /**
//     * Update product.
//     */
//    @PutMapping("/{productId}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
//    public ResponseEntity<ProductDTO> update(
//            @PathVariable Long storeId,
//            @PathVariable Long productId,
//            @RequestBody ProductDTO productDTO) {
//        Long tenantId = getTenantId();
//        productDTO.setTenantId(tenantId);
//        return ResponseEntity.ok(productService.update(tenantId, productId, productDTO));
//    }
//
//    /**
//     * Delete product.
//     */
//    @DeleteMapping("/{productId}")
//    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
//    public ResponseEntity<Void> delete(
//            @PathVariable Long storeId,
//            @PathVariable Long productId) {
//        Long tenantId = getTenantId();
//        productService.delete(tenantId, productId);
//        return ResponseEntity.noContent().build();
//    }
//
//    /**
//     * Safety check for tenant resolution.
//     */
//    private Long getTenantId() {
//        Long tenantId = TenantContext.getTenantId();
//        if (tenantId == null) {
//            throw new IllegalStateException("Tenant not resolved. Please access via correct subdomain.");
//        }
//        return tenantId;
//    }
}