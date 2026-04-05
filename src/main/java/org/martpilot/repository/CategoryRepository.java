package org.martpilot.repository;

import org.martpilot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByTenantIdAndStoreId(Long tenantId,  Long storeId);

    Optional<Category> findByIdAndTenantId(Long id, Long tenantId);
}

