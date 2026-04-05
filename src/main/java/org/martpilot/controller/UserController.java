package org.martpilot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.martpilot.dto.*;
import org.martpilot.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(
            @RequestHeader("X-Tenant-ID") Long tenantId) {
        List<UserResponse> users = userService.getAllUsers(tenantId);
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", users));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long id,
            @RequestHeader("X-Tenant-ID") Long tenantId) {
        UserResponse user = userService.getUserById(id, tenantId);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user));
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @RequestHeader("X-Tenant-ID") Long tenantId,
            @Valid @RequestBody UserRequest request) {
        UserResponse user = userService.createUser(request, tenantId);
        return ResponseEntity.status(201).body(ApiResponse.success("User created successfully", user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @RequestHeader("X-Tenant-ID") Long tenantId,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse user = userService.updateUser(id, request, tenantId);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long id,
            @RequestHeader("X-Tenant-ID") Long tenantId) {
        userService.softDeleteUser(id, tenantId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }
}

