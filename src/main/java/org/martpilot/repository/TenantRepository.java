package org.martpilot.repository;

import org.martpilot.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    Optional<Tenant> findBySubdomain(String subdomain);
    boolean existsBySubdomain(String subdomain);

    @Query("SELECT t FROM Tenant t WHERE t.status = 'ACTIVE'")
    Collection<Tenant> findActiveTenant();}

