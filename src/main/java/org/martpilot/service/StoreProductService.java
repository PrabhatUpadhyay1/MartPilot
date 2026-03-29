package org.martpilot.service;

import org.martpilot.dto.StoreProductDTO;

import java.util.List;

public interface StoreProductService {

    StoreProductDTO create(Long tenantId, StoreProductDTO storeProductDTO);

    StoreProductDTO getById(Long tenantId, Long storeProductId);

    List<StoreProductDTO> getByStoreId(Long tenantId, Long storeId);

    List<StoreProductDTO> getByProductId(Long tenantId, Long productId);

    StoreProductDTO getByStoreIdAndProductId(Long tenantId, Long storeId, Long productId);

    StoreProductDTO update(Long tenantId, Long storeProductId, StoreProductDTO storeProductDTO);

    void delete(Long tenantId, Long storeProductId);
}

