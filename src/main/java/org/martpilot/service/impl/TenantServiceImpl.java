package org.martpilot.service.impl;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.TenantDTO;
import org.martpilot.entity.Tenant;
import org.martpilot.exception.ResourceNotFoundException;
import org.martpilot.repository.TenantRepository;
import org.martpilot.service.TenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    @Override
    public TenantDTO create(TenantDTO tenantDTO) {
        Tenant tenant = Tenant.builder()
                .businessName(tenantDTO.getBusinessName())
                .subdomain(tenantDTO.getSubdomain())
                .customDomain(tenantDTO.getCustomDomain())
                .ownerName(tenantDTO.getOwnerName())
                .phone(tenantDTO.getPhone())
                .email(tenantDTO.getEmail())
                .subscriptionPlan(tenantDTO.getSubscriptionPlan())
                .isActive(true)
                .build();
        
        Tenant savedTenant = tenantRepository.save(tenant);
        return mapToDTO(savedTenant);
    }

    @Override
    @Transactional(readOnly = true)
    public TenantDTO getById(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", id));
        return mapToDTO(tenant);
    }

    @Override
    @Transactional(readOnly = true)
    public TenantDTO getBySubdomain(String subdomain) {
        Tenant tenant = tenantRepository.findBySubdomain(subdomain)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "subdomain", subdomain));
        return mapToDTO(tenant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TenantDTO> getAll() {
        return tenantRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TenantDTO> getAllActive() {
        return tenantRepository.findAllActive()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TenantDTO update(Long id, TenantDTO tenantDTO) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", id));
        
        if (tenantDTO.getBusinessName() != null) {
            tenant.setBusinessName(tenantDTO.getBusinessName());
        }
        if (tenantDTO.getCustomDomain() != null) {
            tenant.setCustomDomain(tenantDTO.getCustomDomain());
        }
        if (tenantDTO.getOwnerName() != null) {
            tenant.setOwnerName(tenantDTO.getOwnerName());
        }
        if (tenantDTO.getPhone() != null) {
            tenant.setPhone(tenantDTO.getPhone());
        }
        if (tenantDTO.getEmail() != null) {
            tenant.setEmail(tenantDTO.getEmail());
        }
        if (tenantDTO.getSubscriptionPlan() != null) {
            tenant.setSubscriptionPlan(tenantDTO.getSubscriptionPlan());
        }
        if (tenantDTO.getIsActive() != null) {
            tenant.setIsActive(tenantDTO.getIsActive());
        }
        
        Tenant updatedTenant = tenantRepository.save(tenant);
        return mapToDTO(updatedTenant);
    }

    @Override
    public void delete(Long id) {
        Tenant tenant = tenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", id));
        tenantRepository.delete(tenant);
    }

    private TenantDTO mapToDTO(Tenant tenant) {
        return TenantDTO.builder()
                .id(tenant.getId())
                .businessName(tenant.getBusinessName())
                .subdomain(tenant.getSubdomain())
                .customDomain(tenant.getCustomDomain())
                .ownerName(tenant.getOwnerName())
                .phone(tenant.getPhone())
                .email(tenant.getEmail())
                .subscriptionPlan(tenant.getSubscriptionPlan())
                .isActive(tenant.getIsActive())
                .createdAt(tenant.getCreatedAt())
                .build();
    }
}

