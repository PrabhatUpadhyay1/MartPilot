package org.martpilot.controller;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.OrderItemDTO;
import org.martpilot.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<OrderItemDTO> create(@RequestBody OrderItemDTO orderItemDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemService.create(orderItemDTO));
    }

    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItemDTO> getById(@PathVariable Long orderItemId) {
        return ResponseEntity.ok(orderItemService.getById(orderItemId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItemDTO>> getByOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderItemService.getByOrderId(orderId));
    }

    @PutMapping("/{orderItemId}")
    public ResponseEntity<OrderItemDTO> update(
            @PathVariable Long orderItemId,
            @RequestBody OrderItemDTO orderItemDTO) {
        return ResponseEntity.ok(orderItemService.update(orderItemId, orderItemDTO));
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> delete(@PathVariable Long orderItemId) {
        orderItemService.delete(orderItemId);
        return ResponseEntity.noContent().build();
    }
}

