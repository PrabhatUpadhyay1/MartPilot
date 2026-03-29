package org.martpilot.service;

import org.martpilot.dto.OrderItemDTO;

import java.util.List;

public interface OrderItemService {

    OrderItemDTO create(OrderItemDTO orderItemDTO);

    OrderItemDTO getById(Long orderItemId);

    List<OrderItemDTO> getByOrderId(Long orderId);

    OrderItemDTO update(Long orderItemId, OrderItemDTO orderItemDTO);

    void delete(Long orderItemId);
}

