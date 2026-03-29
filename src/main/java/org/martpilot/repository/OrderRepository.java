package org.martpilot.repository;

import org.martpilot.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByTenantId(Long tenantId);

    List<Order> findByUserId(Long userId);

    List<Order> findByStoreId(Long storeId);

    Optional<Order> findByIdAndTenantId(Long id, Long tenantId);

    List<Order> findByUserIdAndTenantId(Long userId, Long tenantId);

    List<Order> findByStoreIdAndTenantId(Long storeId, Long tenantId);
}

