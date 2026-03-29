package org.martpilot.service.impl;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.OrderItemDTO;
import org.martpilot.entity.Order;
import org.martpilot.entity.OrderItem;
import org.martpilot.entity.Product;
import org.martpilot.exception.ResourceNotFoundException;
import org.martpilot.repository.OrderItemRepository;
import org.martpilot.repository.OrderRepository;
import org.martpilot.repository.ProductRepository;
import org.martpilot.service.OrderItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderItemDTO create(OrderItemDTO orderItemDTO) {
        Order order = orderRepository.findById(orderItemDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderItemDTO.getOrderId()));
        
        Product product = productRepository.findById(orderItemDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", orderItemDTO.getProductId()));
        
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(orderItemDTO.getQuantity())
                .price(orderItemDTO.getPrice())
                .build();
        
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);
        return mapToDTO(savedOrderItem);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemDTO getById(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", orderItemId));
        return mapToDTO(orderItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItemDTO> getByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDTO update(Long orderItemId, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", orderItemId));
        
        if (orderItemDTO.getQuantity() != null) {
            orderItem.setQuantity(orderItemDTO.getQuantity());
        }
        if (orderItemDTO.getPrice() != null) {
            orderItem.setPrice(orderItemDTO.getPrice());
        }
        
        OrderItem updatedOrderItem = orderItemRepository.save(orderItem);
        return mapToDTO(updatedOrderItem);
    }

    @Override
    public void delete(Long orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderItem", "id", orderItemId));
        orderItemRepository.delete(orderItem);
    }

    private OrderItemDTO mapToDTO(OrderItem orderItem) {
        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .orderId(orderItem.getOrder().getId())
                .productId(orderItem.getProduct().getId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }
}

