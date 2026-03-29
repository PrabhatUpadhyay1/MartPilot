package org.martpilot.service;

import org.martpilot.dto.UserTenantDTO;

import java.util.List;

public interface UserTenantService {

    UserTenantDTO create(UserTenantDTO userTenantDTO);

    UserTenantDTO getById(Long id);

    List<UserTenantDTO> getByUserId(Long userId);

    List<UserTenantDTO> getByTenantId(Long tenantId);

    UserTenantDTO getByUserIdAndTenantId(Long userId, Long tenantId);

    void delete(Long id);

    void deleteByUserIdAndTenantId(Long userId, Long tenantId);
}

