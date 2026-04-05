package org.martpilot.service;

import org.martpilot.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO create(Long tenantId, CategoryDTO categoryDTO);

    CategoryDTO getById(Long tenantId, Long categoryId);

    List<CategoryDTO> getByTenantId(Long tenantId, Long storeId);

    CategoryDTO update(Long tenantId, Long categoryId, CategoryDTO categoryDTO);

    void delete(Long tenantId, Long categoryId);
}

