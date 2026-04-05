package org.martpilot.service.impl;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.CategoryDTO;
import org.martpilot.entity.Category;
import org.martpilot.entity.Store;
import org.martpilot.entity.Tenant;
import org.martpilot.exception.ResourceNotFoundException;
import org.martpilot.exception.TenantAccessDeniedException;
import org.martpilot.repository.CategoryRepository;
import org.martpilot.repository.StoreRepository;
import org.martpilot.repository.TenantRepository;
import org.martpilot.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final TenantRepository tenantRepository;

    @Override
    public CategoryDTO create(Long tenantId, CategoryDTO categoryDTO) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", tenantId));

        Store store = null;
        if (categoryDTO.getStoreId() != null) {
            store = storeRepository.findByIdAndTenantId(categoryDTO.getStoreId(), tenantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store is not present", "id", categoryDTO.getStoreId()));
        }
        Category category = Category.builder()
                .tenant(tenant)
                .name(categoryDTO.getName())
                .imageUrl(categoryDTO.getImageUrl())
                .store(store)
                .build();
        
        Category savedCategory = categoryRepository.save(category);
        return mapToDTO(savedCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getById(Long tenantId, Long categoryId) {
        Category category = categoryRepository.findByIdAndTenantId(categoryId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        
        validateTenantAccess(category, tenantId);
        return mapToDTO(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> getByTenantId(Long tenantId, Long storeId) {
        return categoryRepository.findByTenantIdAndStoreId(tenantId, storeId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @Override
    public CategoryDTO update(Long tenantId, Long categoryId, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findByIdAndTenantId(categoryId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        
        validateTenantAccess(category, tenantId);
        
        if (categoryDTO.getName() != null) {
            category.setName(categoryDTO.getName());
        }
        if (categoryDTO.getImageUrl() != null) {
            category.setImageUrl(categoryDTO.getImageUrl());
        }
        
        Category updatedCategory = categoryRepository.save(category);
        return mapToDTO(updatedCategory);
    }

    @Override
    public void delete(Long tenantId, Long categoryId) {
        Category category = categoryRepository.findByIdAndTenantId(categoryId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        
        validateTenantAccess(category, tenantId);
        categoryRepository.delete(category);
    }

    private void validateTenantAccess(Category category, Long tenantId) {
        if (!category.getTenant().getId().equals(tenantId)) {
            throw new TenantAccessDeniedException(tenantId, category.getTenant().getId());
        }
    }

    private CategoryDTO mapToDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .tenantId(category.getTenant().getId())
                .name(category.getName())
                .imageUrl(category.getImageUrl())
                .storeId(category.getStore() != null ? category.getStore().getId() : null)
                .build();
    }
}

