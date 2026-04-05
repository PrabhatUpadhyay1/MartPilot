package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.TenantDTO;
import org.martpilot.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantDTO> create(@RequestBody TenantDTO tenantDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tenantService.create(tenantDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.getById(id));
    }

    @GetMapping("/subdomain/{subdomain}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantDTO> getBySubdomain(@PathVariable String subdomain) {
        return ResponseEntity.ok(tenantService.getBySubdomain(subdomain));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<TenantDTO>> getAll() {
        return ResponseEntity.ok(tenantService.getAll());
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<TenantDTO>> getAllActive() {
        return ResponseEntity.ok(tenantService.getAllActive());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<TenantDTO> update(
            @PathVariable Long id,
            @RequestBody TenantDTO tenantDTO) {
        return ResponseEntity.ok(tenantService.update(id, tenantDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tenantService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

