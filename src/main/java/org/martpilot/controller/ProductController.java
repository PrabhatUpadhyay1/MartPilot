package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.ProductDTO;
import org.martpilot.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants/{tenantId}/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> create(
            @PathVariable Long tenantId,
            @RequestBody ProductDTO productDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(tenantId, productDTO));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getById(
            @PathVariable Long tenantId,
            @PathVariable Long productId) {
        return ResponseEntity.ok(productService.getById(tenantId, productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getByTenantId(@PathVariable Long tenantId) {
        return ResponseEntity.ok(productService.getByTenantId(tenantId));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getByCategory(
            @PathVariable Long tenantId,
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getByTenantIdAndCategoryId(tenantId, categoryId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> update(
            @PathVariable Long tenantId,
            @PathVariable Long productId,
            @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.update(tenantId, productId, productDTO));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long tenantId,
            @PathVariable Long productId) {
        productService.delete(tenantId, productId);
        return ResponseEntity.noContent().build();
    }
}

