package org.martpilot.service;

import org.martpilot.dto.StoreDTO;

import java.util.List;

public interface StoreService {

    StoreDTO create(Long tenantId, StoreDTO storeDTO);

    StoreDTO getById(Long tenantId, Long storeId);

    List<StoreDTO> getByTenantId(Long tenantId);

    List<StoreDTO> getActiveStoresByTenantId(Long tenantId);

    StoreDTO update(Long tenantId, Long storeId, StoreDTO storeDTO);

    void delete(Long tenantId, Long storeId);
}

