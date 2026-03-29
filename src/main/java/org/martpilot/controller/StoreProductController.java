package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.StoreProductDTO;
import org.martpilot.service.StoreProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants/{tenantId}/store-products")
@RequiredArgsConstructor
public class StoreProductController {

    private final StoreProductService storeProductService;

    @PostMapping
    public ResponseEntity<StoreProductDTO> create(
            @PathVariable Long tenantId,
            @RequestBody StoreProductDTO storeProductDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeProductService.create(tenantId, storeProductDTO));
    }

    @GetMapping("/{storeProductId}")
    public ResponseEntity<StoreProductDTO> getById(
            @PathVariable Long tenantId,
            @PathVariable Long storeProductId) {
        return ResponseEntity.ok(storeProductService.getById(tenantId, storeProductId));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<StoreProductDTO>> getByStoreId(
            @PathVariable Long tenantId,
            @PathVariable Long storeId) {
        return ResponseEntity.ok(storeProductService.getByStoreId(tenantId, storeId));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StoreProductDTO>> getByProductId(
            @PathVariable Long tenantId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(storeProductService.getByProductId(tenantId, productId));
    }

    @GetMapping("/store/{storeId}/product/{productId}")
    public ResponseEntity<StoreProductDTO> getByStoreIdAndProductId(
            @PathVariable Long tenantId,
            @PathVariable Long storeId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(storeProductService.getByStoreIdAndProductId(tenantId, storeId, productId));
    }

    @PutMapping("/{storeProductId}")
    public ResponseEntity<StoreProductDTO> update(
            @PathVariable Long tenantId,
            @PathVariable Long storeProductId,
            @RequestBody StoreProductDTO storeProductDTO) {
        return ResponseEntity.ok(storeProductService.update(tenantId, storeProductId, storeProductDTO));
    }

    @DeleteMapping("/{storeProductId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long tenantId,
            @PathVariable Long storeProductId) {
        storeProductService.delete(tenantId, storeProductId);
        return ResponseEntity.noContent().build();
    }
}

