package org.martpilot.service.impl;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.OrderDTO;
import org.martpilot.entity.Order;
import org.martpilot.entity.Store;
import org.martpilot.entity.Tenant;
import org.martpilot.entity.User;
import org.martpilot.exception.ResourceNotFoundException;
import org.martpilot.exception.TenantAccessDeniedException;
import org.martpilot.repository.OrderRepository;
import org.martpilot.repository.StoreRepository;
import org.martpilot.repository.TenantRepository;
import org.martpilot.repository.UserRepository;
import org.martpilot.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Override
    public OrderDTO create(Long tenantId, OrderDTO orderDTO) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", tenantId));
        
        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", orderDTO.getUserId()));
        
        Store store = storeRepository.findByIdAndTenantId(orderDTO.getStoreId(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", orderDTO.getStoreId()));
        
        Order order = Order.builder()
                .tenant(tenant)
                .user(user)
                .store(store)
                .totalAmount(orderDTO.getTotalAmount())
                .status(orderDTO.getStatus())
                .paymentMethod(orderDTO.getPaymentMethod())
                .paymentStatus(orderDTO.getPaymentStatus())
                .build();
        
        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getById(Long tenantId, Long orderId) {
        Order order = orderRepository.findByIdAndTenantId(orderId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        
        validateTenantAccess(order, tenantId);
        return mapToDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getByTenantId(Long tenantId) {
        return orderRepository.findByTenantId(tenantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getByUserId(Long tenantId, Long userId) {
        return orderRepository.findByUserIdAndTenantId(userId, tenantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getByStoreId(Long tenantId, Long storeId) {
        return orderRepository.findByStoreIdAndTenantId(storeId, tenantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO update(Long tenantId, Long orderId, OrderDTO orderDTO) {
        Order order = orderRepository.findByIdAndTenantId(orderId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        
        validateTenantAccess(order, tenantId);
        
        if (orderDTO.getTotalAmount() != null) {
            order.setTotalAmount(orderDTO.getTotalAmount());
        }
        if (orderDTO.getStatus() != null) {
            order.setStatus(orderDTO.getStatus());
        }
        if (orderDTO.getPaymentMethod() != null) {
            order.setPaymentMethod(orderDTO.getPaymentMethod());
        }
        if (orderDTO.getPaymentStatus() != null) {
            order.setPaymentStatus(orderDTO.getPaymentStatus());
        }
        
        Order updatedOrder = orderRepository.save(order);
        return mapToDTO(updatedOrder);
    }

    @Override
    public void delete(Long tenantId, Long orderId) {
        Order order = orderRepository.findByIdAndTenantId(orderId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));
        
        validateTenantAccess(order, tenantId);
        orderRepository.delete(order);
    }

    private void validateTenantAccess(Order order, Long tenantId) {
        if (!order.getTenant().getId().equals(tenantId)) {
            throw new TenantAccessDeniedException(tenantId, order.getTenant().getId());
        }
    }

    private OrderDTO mapToDTO(Order order) {
        return OrderDTO.builder()
                .id(order.getId())
                .tenantId(order.getTenant().getId())
                .userId(order.getUser().getId())
                .storeId(order.getStore().getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .paymentStatus(order.getPaymentStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}

