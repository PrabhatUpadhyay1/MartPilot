package org.martpilot.service;

import org.martpilot.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderDTO create(Long tenantId, OrderDTO orderDTO);

    OrderDTO getById(Long tenantId, Long orderId);

    List<OrderDTO> getByTenantId(Long tenantId);

    List<OrderDTO> getByUserId(Long tenantId, Long userId);

    List<OrderDTO> getByStoreId(Long tenantId, Long storeId);

    OrderDTO update(Long tenantId, Long orderId, OrderDTO orderDTO);

    void delete(Long tenantId, Long orderId);
}

