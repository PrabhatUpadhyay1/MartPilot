package org.martpilot.service;

import org.martpilot.dto.OrderItemDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {

    OrderItemDTO create(OrderItemDTO orderItemDTO);

    OrderItemDTO getById(Long orderItemId);

    List<OrderItemDTO> getByOrderId(Long orderId);

    OrderItemDTO update(Long orderItemId, OrderItemDTO orderItemDTO);

    void delete(Long orderItemId);
}

