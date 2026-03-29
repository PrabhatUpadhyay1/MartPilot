package org.martpilot.repository;

import org.martpilot.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByTenantId(Long tenantId);

    Optional<Store> findByIdAndTenantId(Long id, Long tenantId);

    @Query("SELECT s FROM Store s WHERE s.tenant.id = :tenantId AND s.isActive = true")
    List<Store> findActiveStoresByTenantId(@Param("tenantId") Long tenantId);
}

