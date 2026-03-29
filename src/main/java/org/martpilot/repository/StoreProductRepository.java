package org.martpilot.repository;

import org.martpilot.entity.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, Long> {

    List<StoreProduct> findByTenantId(Long tenantId);

    List<StoreProduct> findByStoreId(Long storeId);

    List<StoreProduct> findByProductId(Long productId);

    Optional<StoreProduct> findByIdAndTenantId(Long id, Long tenantId);

    Optional<StoreProduct> findByStoreIdAndProductId(Long storeId, Long productId);

    List<StoreProduct> findByStoreIdAndTenantId(Long storeId, Long tenantId);
}

