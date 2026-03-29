package org.martpilot.repository;

import org.martpilot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTenantId(Long tenantId);

    Optional<Product> findByIdAndTenantId(Long id, Long tenantId);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByTenantIdAndCategoryId(Long tenantId, Long categoryId);
}

