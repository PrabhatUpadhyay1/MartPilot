package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.UserTenantDTO;
import org.martpilot.service.UserTenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-tenants")
@RequiredArgsConstructor
public class UserTenantController {

    private final UserTenantService userTenantService;

    @PostMapping
    public ResponseEntity<UserTenantDTO> create(@RequestBody UserTenantDTO userTenantDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userTenantService.create(userTenantDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserTenantDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userTenantService.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTenantDTO>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userTenantService.getByUserId(userId));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<UserTenantDTO>> getByTenantId(@PathVariable Long tenantId) {
        return ResponseEntity.ok(userTenantService.getByTenantId(tenantId));
    }

    @GetMapping("/user/{userId}/tenant/{tenantId}")
    public ResponseEntity<UserTenantDTO> getByUserIdAndTenantId(
            @PathVariable Long userId,
            @PathVariable Long tenantId) {
        return ResponseEntity.ok(userTenantService.getByUserIdAndTenantId(userId, tenantId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userTenantService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{userId}/tenant/{tenantId}")
    public ResponseEntity<Void> deleteByUserIdAndTenantId(
            @PathVariable Long userId,
            @PathVariable Long tenantId) {
        userTenantService.deleteByUserIdAndTenantId(userId, tenantId);
        return ResponseEntity.noContent().build();
    }
}

