package org.martpilot.service;

import org.martpilot.dto.TenantDTO;

import java.util.List;

public interface TenantService {

    TenantDTO create(TenantDTO tenantDTO);

    TenantDTO getById(Long id);

    TenantDTO getBySubdomain(String subdomain);

    List<TenantDTO> getAll();

    List<TenantDTO> getAllActive();

    TenantDTO update(Long id, TenantDTO tenantDTO);

    void delete(Long id);
}

