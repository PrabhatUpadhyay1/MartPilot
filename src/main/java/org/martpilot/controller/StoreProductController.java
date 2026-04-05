package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.context.TenantContext;
import org.martpilot.dto.StoreProductDTO;
import org.martpilot.service.StoreProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StoreProductController - Tenant-scoped store product/inventory management.
 * Tenant is resolved from subdomain.
 */
@RestController
@RequestMapping("/api/v1/store-products")
@RequiredArgsConstructor
public class StoreProductController {

    private final StoreProductService storeProductService;

    /**
     * Create store product (inventory).
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<StoreProductDTO> create(@RequestBody StoreProductDTO storeProductDTO) {
        Long tenantId = getTenantId();
        storeProductDTO.setTenantId(tenantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(storeProductService.create(tenantId, storeProductDTO));
    }

    /**
     * Get store product by ID.
     */
    @GetMapping("/{storeProductId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
    public ResponseEntity<StoreProductDTO> getById(@PathVariable Long storeProductId) {
        Long tenantId = getTenantId();
        return ResponseEntity.ok(storeProductService.getById(tenantId, storeProductId));
    }

    /**
     * Get all products for a store.
     */
    @GetMapping("/store/{storeId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
    public ResponseEntity<List<StoreProductDTO>> getByStoreId(@PathVariable Long storeId) {
        Long tenantId = getTenantId();
        return ResponseEntity.ok(storeProductService.getByStoreId(tenantId, storeId));
    }

    /**
     * Get all stores for a product.
     */
    @GetMapping("/product/{productId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<List<StoreProductDTO>> getByProductId(@PathVariable Long productId) {
        Long tenantId = getTenantId();
        return ResponseEntity.ok(storeProductService.getByProductId(tenantId, productId));
    }

    /**
     * Get store product by store and product.
     */
    @GetMapping("/store/{storeId}/product/{productId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
    public ResponseEntity<StoreProductDTO> getByStoreIdAndProductId(
            @PathVariable Long storeId,
            @PathVariable Long productId) {
        Long tenantId = getTenantId();
        return ResponseEntity.ok(storeProductService.getByStoreIdAndProductId(tenantId, storeId, productId));
    }

    /**
     * Update store product.
     */
    @PutMapping("/{storeProductId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<StoreProductDTO> update(
            @PathVariable Long storeProductId,
            @RequestBody StoreProductDTO storeProductDTO) {
        Long tenantId = getTenantId();
        storeProductDTO.setTenantId(tenantId);
        return ResponseEntity.ok(storeProductService.update(tenantId, storeProductId, storeProductDTO));
    }

    /**
     * Delete store product.
     */
    @DeleteMapping("/{storeProductId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long storeProductId) {
        Long tenantId = getTenantId();
        storeProductService.delete(tenantId, storeProductId);
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