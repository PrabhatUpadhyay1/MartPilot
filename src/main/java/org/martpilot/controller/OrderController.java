package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.OrderDTO;
import org.martpilot.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants/{tenantId}/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDTO> create(
            @PathVariable Long tenantId,
            @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(tenantId, orderDTO));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getById(
            @PathVariable Long tenantId,
            @PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getById(tenantId, orderId));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getByTenantId(@PathVariable Long tenantId) {
        return ResponseEntity.ok(orderService.getByTenantId(tenantId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getByUserId(
            @PathVariable Long tenantId,
            @PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getByUserId(tenantId, userId));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<OrderDTO>> getByStoreId(
            @PathVariable Long tenantId,
            @PathVariable Long storeId) {
        return ResponseEntity.ok(orderService.getByStoreId(tenantId, storeId));
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> update(
            @PathVariable Long tenantId,
            @PathVariable Long orderId,
            @RequestBody OrderDTO orderDTO) {
        return ResponseEntity.ok(orderService.update(tenantId, orderId, orderDTO));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long tenantId,
            @PathVariable Long orderId) {
        orderService.delete(tenantId, orderId);
        return ResponseEntity.noContent().build();
    }
}

