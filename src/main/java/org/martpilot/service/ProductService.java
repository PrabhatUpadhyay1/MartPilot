package org.martpilot.service;

import org.martpilot.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO create(Long categoryId, ProductDTO productDTO);

    ProductDTO getById(Long tenantId, Long productId);

    List<ProductDTO> getByTenantId(Long tenantId, Long storeId);

    List<ProductDTO> getByTenantIdAndCategoryId(Long tenantId, Long categoryId);

    ProductDTO update(Long tenantId, Long productId, ProductDTO productDTO);

    void delete(Long tenantId, Long productId);
}

