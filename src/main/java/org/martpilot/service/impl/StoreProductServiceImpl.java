package org.martpilot.service.impl;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.StoreProductDTO;
import org.martpilot.entity.Product;
import org.martpilot.entity.Store;
import org.martpilot.entity.StoreProduct;
import org.martpilot.entity.Tenant;
import org.martpilot.exception.ResourceNotFoundException;
import org.martpilot.exception.TenantAccessDeniedException;
import org.martpilot.repository.ProductRepository;
import org.martpilot.repository.StoreProductRepository;
import org.martpilot.repository.StoreRepository;
import org.martpilot.repository.TenantRepository;
import org.martpilot.service.StoreProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreProductServiceImpl implements StoreProductService {

    private final StoreProductRepository storeProductRepository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final TenantRepository tenantRepository;

    @Override
    public StoreProductDTO create(Long tenantId, StoreProductDTO storeProductDTO) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", tenantId));
        
        Store store = storeRepository.findByIdAndTenantId(storeProductDTO.getStoreId(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", storeProductDTO.getStoreId()));
        
        Product product = productRepository.findByIdAndTenantId(storeProductDTO.getProductId(), tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", storeProductDTO.getProductId()));
        
        StoreProduct storeProduct = StoreProduct.builder()
                .tenant(tenant)
                .store(store)
                .product(product)
                .price(storeProductDTO.getPrice())
                .stock(storeProductDTO.getStock())
                .isAvailable(true)
                .build();
        
        StoreProduct savedStoreProduct = storeProductRepository.save(storeProduct);
        return mapToDTO(savedStoreProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public StoreProductDTO getById(Long tenantId, Long storeProductId) {
        StoreProduct storeProduct = storeProductRepository.findByIdAndTenantId(storeProductId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("StoreProduct", "id", storeProductId));
        
        validateTenantAccess(storeProduct, tenantId);
        return mapToDTO(storeProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreProductDTO> getByStoreId(Long tenantId, Long storeId) {
        return storeProductRepository.findByStoreIdAndTenantId(storeId, tenantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreProductDTO> getByProductId(Long tenantId, Long productId) {
        return storeProductRepository.findByProductId(productId)
                .stream()
                .filter(sp -> sp.getTenant().getId().equals(tenantId))
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StoreProductDTO getByStoreIdAndProductId(Long tenantId, Long storeId, Long productId) {
        StoreProduct storeProduct = storeProductRepository.findByStoreIdAndProductId(storeId, productId)
                .orElseThrow(() -> new ResourceNotFoundException("StoreProduct", "storeId,productId", 
                        storeId + "," + productId));
        
        validateTenantAccess(storeProduct, tenantId);
        return mapToDTO(storeProduct);
    }

    @Override
    public StoreProductDTO update(Long tenantId, Long storeProductId, StoreProductDTO storeProductDTO) {
        StoreProduct storeProduct = storeProductRepository.findByIdAndTenantId(storeProductId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("StoreProduct", "id", storeProductId));
        
        validateTenantAccess(storeProduct, tenantId);
        
        if (storeProductDTO.getPrice() != null) {
            storeProduct.setPrice(storeProductDTO.getPrice());
        }
        if (storeProductDTO.getStock() != null) {
            storeProduct.setStock(storeProductDTO.getStock());
        }
        if (storeProductDTO.getIsAvailable() != null) {
            storeProduct.setIsAvailable(storeProductDTO.getIsAvailable());
        }
        
        StoreProduct updatedStoreProduct = storeProductRepository.save(storeProduct);
        return mapToDTO(updatedStoreProduct);
    }

    @Override
    public void delete(Long tenantId, Long storeProductId) {
        StoreProduct storeProduct = storeProductRepository.findByIdAndTenantId(storeProductId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("StoreProduct", "id", storeProductId));
        
        validateTenantAccess(storeProduct, tenantId);
        storeProductRepository.delete(storeProduct);
    }

    private void validateTenantAccess(StoreProduct storeProduct, Long tenantId) {
        if (!storeProduct.getTenant().getId().equals(tenantId)) {
            throw new TenantAccessDeniedException(tenantId, storeProduct.getTenant().getId());
        }
    }

    private StoreProductDTO mapToDTO(StoreProduct storeProduct) {
        return StoreProductDTO.builder()
                .id(storeProduct.getId())
                .tenantId(storeProduct.getTenant().getId())
                .storeId(storeProduct.getStore().getId())
                .productId(storeProduct.getProduct().getId())
                .price(storeProduct.getPrice())
                .stock(storeProduct.getStock())
                .isAvailable(storeProduct.getIsAvailable())
                .build();
    }
}

