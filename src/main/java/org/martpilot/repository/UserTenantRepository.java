package org.martpilot.repository;

import org.martpilot.entity.UserTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTenantRepository extends JpaRepository<UserTenant, Long> {

    Optional<UserTenant> findByUserIdAndTenantId(Long userId, Long tenantId);
    List<UserTenant> findAllByTenantId(Long tenantId);
    List<UserTenant> findAllByUserId(Long userId);
    boolean existsByUserIdAndTenantId(Long userId, Long tenantId);
    Optional<UserTenant> findByUserIdAndTenantIdAndRole(Long userId, Long tenantId, String role);
    Optional<UserTenant> findByUserId(Long userId);

    Optional<UserTenant> findByTenantId(Long tenantId);

    void deleteByUserIdAndTenantId(Long userId, Long tenantId);

    /**
     * Find user-tenant by user email and tenant ID.
     * Used for tenant-specific login.
     */
    Optional<UserTenant> findByUserEmailAndTenantId(String email, Long tenantId);
}

