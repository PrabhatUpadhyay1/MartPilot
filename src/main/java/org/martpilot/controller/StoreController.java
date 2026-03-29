package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.StoreDTO;
import org.martpilot.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants/{tenantId}/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<StoreDTO> create(
            @PathVariable Long tenantId,
            @RequestBody StoreDTO storeDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeService.create(tenantId, storeDTO));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreDTO> getById(
            @PathVariable Long tenantId,
            @PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.getById(tenantId, storeId));
    }

    @GetMapping
    public ResponseEntity<List<StoreDTO>> getByTenantId(@PathVariable Long tenantId) {
        return ResponseEntity.ok(storeService.getByTenantId(tenantId));
    }

    @GetMapping("/active")
    public ResponseEntity<List<StoreDTO>> getActiveStores(@PathVariable Long tenantId) {
        return ResponseEntity.ok(storeService.getActiveStoresByTenantId(tenantId));
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<StoreDTO> update(
            @PathVariable Long tenantId,
            @PathVariable Long storeId,
            @RequestBody StoreDTO storeDTO) {
        return ResponseEntity.ok(storeService.update(tenantId, storeId, storeDTO));
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long tenantId,
            @PathVariable Long storeId) {
        storeService.delete(tenantId, storeId);
        return ResponseEntity.noContent().build();
    }
}

