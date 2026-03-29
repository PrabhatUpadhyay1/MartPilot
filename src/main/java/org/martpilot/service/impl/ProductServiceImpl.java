package org.martpilot.service.impl;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.ProductDTO;
import org.martpilot.entity.Category;
import org.martpilot.entity.Product;
import org.martpilot.entity.Tenant;
import org.martpilot.exception.ResourceNotFoundException;
import org.martpilot.exception.TenantAccessDeniedException;
import org.martpilot.repository.CategoryRepository;
import org.martpilot.repository.ProductRepository;
import org.martpilot.repository.TenantRepository;
import org.martpilot.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final TenantRepository tenantRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductDTO create(Long tenantId, ProductDTO productDTO) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", tenantId));
        
        Category category = null;
        if (productDTO.getCategoryId() != null) {
            category = categoryRepository.findByIdAndTenantId(productDTO.getCategoryId(), tenantId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", productDTO.getCategoryId()));
        }
        
        Product product = Product.builder()
                .tenant(tenant)
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .category(category)
                .imageUrl(productDTO.getImageUrl())
                .brand(productDTO.getBrand())
                .build();
        
        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getById(Long tenantId, Long productId) {
        Product product = productRepository.findByIdAndTenantId(productId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        
        validateTenantAccess(product, tenantId);
        return mapToDTO(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getByTenantId(Long tenantId) {
        return productRepository.findByTenantId(tenantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getByTenantIdAndCategoryId(Long tenantId, Long categoryId) {
        return productRepository.findByTenantIdAndCategoryId(tenantId, categoryId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO update(Long tenantId, Long productId, ProductDTO productDTO) {
        Product product = productRepository.findByIdAndTenantId(productId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        
        validateTenantAccess(product, tenantId);
        
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getImageUrl() != null) {
            product.setImageUrl(productDTO.getImageUrl());
        }
        if (productDTO.getBrand() != null) {
            product.setBrand(productDTO.getBrand());
        }
        
        Product updatedProduct = productRepository.save(product);
        return mapToDTO(updatedProduct);
    }

    @Override
    public void delete(Long tenantId, Long productId) {
        Product product = productRepository.findByIdAndTenantId(productId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        
        validateTenantAccess(product, tenantId);
        productRepository.delete(product);
    }

    private void validateTenantAccess(Product product, Long tenantId) {
        if (!product.getTenant().getId().equals(tenantId)) {
            throw new TenantAccessDeniedException(tenantId, product.getTenant().getId());
        }
    }

    private ProductDTO mapToDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .tenantId(product.getTenant().getId())
                .name(product.getName())
                .description(product.getDescription())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .imageUrl(product.getImageUrl())
                .brand(product.getBrand())
                .build();
    }
}

