package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.TenantDTO;
import org.martpilot.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<TenantDTO> create(@RequestBody TenantDTO tenantDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tenantService.create(tenantDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TenantDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.getById(id));
    }

    @GetMapping("/subdomain/{subdomain}")
    public ResponseEntity<TenantDTO> getBySubdomain(@PathVariable String subdomain) {
        return ResponseEntity.ok(tenantService.getBySubdomain(subdomain));
    }

    @GetMapping
    public ResponseEntity<List<TenantDTO>> getAll() {
        return ResponseEntity.ok(tenantService.getAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<TenantDTO>> getAllActive() {
        return ResponseEntity.ok(tenantService.getAllActive());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TenantDTO> update(
            @PathVariable Long id,
            @RequestBody TenantDTO tenantDTO) {
        return ResponseEntity.ok(tenantService.update(id, tenantDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tenantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

