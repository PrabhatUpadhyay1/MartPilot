package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.context.TenantContext;
import org.martpilot.dto.StoreDTO;
import org.martpilot.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StoreController - Tenant-scoped store management endpoints.
 *
 * Tenant is resolved from subdomain via TenantFilter and stored in TenantContext.
 * No tenantId needed in URL anymore.
 *
 * Example URLs:
 * - GET /api/v1/stores - Get all stores for current tenant
 * - POST /api/v1/stores - Create new store
 * - GET /api/v1/stores/{storeId} - Get store by ID
 * - PUT /api/v1/stores/{storeId} - Update store
 * - DELETE /api/v1/stores/{storeId} - Delete store
 */
@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * Create a new store for the current tenant.
     * Tenant is automatically resolved from subdomain.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<StoreDTO> create(@RequestBody StoreDTO storeDTO) {
        Long tenantId = TenantContext.getTenantId();
        storeDTO.setTenantId(tenantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.create(storeDTO));
    }

    /**
     * Get store by ID.
     */
    @GetMapping("/{storeId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
    public ResponseEntity<StoreDTO> getById(@PathVariable Long storeId) {
        Long tenantId = TenantContext.getTenantId();
        return ResponseEntity.ok(storeService.getById(tenantId, storeId));
    }

    /**
     * Get all stores for current tenant.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
    public ResponseEntity<List<StoreDTO>> getAll() {
        Long tenantId = TenantContext.getTenantId();
        return ResponseEntity.ok(storeService.getByTenantId(tenantId));
    }

    /**
     * Get only active stores for current tenant.
     */
    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN', 'CUSTOMER')")
    public ResponseEntity<List<StoreDTO>> getActiveStores() {
        Long tenantId = TenantContext.getTenantId();
        return ResponseEntity.ok(storeService.getActiveStoresByTenantId(tenantId));
    }

    /**
     * Update store details.
     */
    @PutMapping("/{storeId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<StoreDTO> update(
            @PathVariable Long storeId,
            @RequestBody StoreDTO storeDTO) {
        Long tenantId = TenantContext.getTenantId();
        storeDTO.setTenantId(tenantId);
        return ResponseEntity.ok(storeService.update(tenantId, storeId, storeDTO));
    }

    /**
     * Delete store.
     */
    @DeleteMapping("/{storeId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TENANT_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long storeId) {
        Long tenantId = TenantContext.getTenantId();
        storeService.delete(tenantId, storeId);
        return ResponseEntity.noContent().build();
    }
}