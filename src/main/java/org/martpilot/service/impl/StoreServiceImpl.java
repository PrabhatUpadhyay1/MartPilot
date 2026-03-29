package org.martpilot.service.impl;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.StoreDTO;
import org.martpilot.entity.Store;
import org.martpilot.entity.Tenant;
import org.martpilot.exception.ResourceNotFoundException;
import org.martpilot.exception.TenantAccessDeniedException;
import org.martpilot.repository.StoreRepository;
import org.martpilot.repository.TenantRepository;
import org.martpilot.service.StoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final TenantRepository tenantRepository;

    @Override
    public StoreDTO create(Long tenantId, StoreDTO storeDTO) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", tenantId));
        
        Store store = Store.builder()
                .tenant(tenant)
                .name(storeDTO.getName())
                .address(storeDTO.getAddress())
                .latitude(storeDTO.getLatitude())
                .longitude(storeDTO.getLongitude())
                .isActive(true)
                .build();
        
        Store savedStore = storeRepository.save(store);
        return mapToDTO(savedStore);
    }

    @Override
    @Transactional(readOnly = true)
    public StoreDTO getById(Long tenantId, Long storeId) {
        Store store = storeRepository.findByIdAndTenantId(storeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", storeId));
        
        validateTenantAccess(store, tenantId);
        return mapToDTO(store);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDTO> getByTenantId(Long tenantId) {
        return storeRepository.findByTenantId(tenantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDTO> getActiveStoresByTenantId(Long tenantId) {
        return storeRepository.findActiveStoresByTenantId(tenantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StoreDTO update(Long tenantId, Long storeId, StoreDTO storeDTO) {
        Store store = storeRepository.findByIdAndTenantId(storeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", storeId));
        
        validateTenantAccess(store, tenantId);
        
        if (storeDTO.getName() != null) {
            store.setName(storeDTO.getName());
        }
        if (storeDTO.getAddress() != null) {
            store.setAddress(storeDTO.getAddress());
        }
        if (storeDTO.getLatitude() != null) {
            store.setLatitude(storeDTO.getLatitude());
        }
        if (storeDTO.getLongitude() != null) {
            store.setLongitude(storeDTO.getLongitude());
        }
        if (storeDTO.getIsActive() != null) {
            store.setIsActive(storeDTO.getIsActive());
        }
        
        Store updatedStore = storeRepository.save(store);
        return mapToDTO(updatedStore);
    }

    @Override
    public void delete(Long tenantId, Long storeId) {
        Store store = storeRepository.findByIdAndTenantId(storeId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", storeId));
        
        validateTenantAccess(store, tenantId);
        storeRepository.delete(store);
    }

    private void validateTenantAccess(Store store, Long tenantId) {
        if (!store.getTenant().getId().equals(tenantId)) {
            throw new TenantAccessDeniedException(tenantId, store.getTenant().getId());
        }
    }

    private StoreDTO mapToDTO(Store store) {
        return StoreDTO.builder()
                .id(store.getId())
                .tenantId(store.getTenant().getId())
                .name(store.getName())
                .address(store.getAddress())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .isActive(store.getIsActive())
                .build();
    }
}

