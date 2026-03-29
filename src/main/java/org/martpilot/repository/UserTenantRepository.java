package org.martpilot.repository;

import org.martpilot.entity.UserTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTenantRepository extends JpaRepository<UserTenant, Long> {

    List<UserTenant> findByUserId(Long userId);

    List<UserTenant> findByTenantId(Long tenantId);

    Optional<UserTenant> findByUserIdAndTenantId(Long userId, Long tenantId);

    void deleteByUserIdAndTenantId(Long userId, Long tenantId);
}

