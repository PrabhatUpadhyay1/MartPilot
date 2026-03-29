package org.martpilot.service.impl;

import lombok.RequiredArgsConstructor;
import org.martpilot.dto.UserTenantDTO;
import org.martpilot.entity.User;
import org.martpilot.entity.UserTenant;
import org.martpilot.entity.Tenant;
import org.martpilot.exception.ResourceNotFoundException;
import org.martpilot.repository.UserRepository;
import org.martpilot.repository.UserTenantRepository;
import org.martpilot.repository.TenantRepository;
import org.martpilot.service.UserTenantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserTenantServiceImpl implements UserTenantService {

    private final UserTenantRepository userTenantRepository;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;

    @Override
    public UserTenantDTO create(UserTenantDTO userTenantDTO) {
        User user = userRepository.findById(userTenantDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userTenantDTO.getUserId()));
        
        Tenant tenant = tenantRepository.findById(userTenantDTO.getTenantId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "id", userTenantDTO.getTenantId()));
        
        UserTenant userTenant = UserTenant.builder()
                .user(user)
                .tenant(tenant)
                .build();
        
        UserTenant savedUserTenant = userTenantRepository.save(userTenant);
        return mapToDTO(savedUserTenant);
    }

    @Override
    @Transactional(readOnly = true)
    public UserTenantDTO getById(Long id) {
        UserTenant userTenant = userTenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserTenant", "id", id));
        return mapToDTO(userTenant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTenantDTO> getByUserId(Long userId) {
        return userTenantRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserTenantDTO> getByTenantId(Long tenantId) {
        return userTenantRepository.findByTenantId(tenantId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserTenantDTO getByUserIdAndTenantId(Long userId, Long tenantId) {
        UserTenant userTenant = userTenantRepository.findByUserIdAndTenantId(userId, tenantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "UserTenant not found for userId: " + userId + " and tenantId: " + tenantId));
        return mapToDTO(userTenant);
    }

    @Override
    public void delete(Long id) {
        UserTenant userTenant = userTenantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserTenant", "id", id));
        userTenantRepository.delete(userTenant);
    }

    @Override
    public void deleteByUserIdAndTenantId(Long userId, Long tenantId) {
        userTenantRepository.deleteByUserIdAndTenantId(userId, tenantId);
    }

    private UserTenantDTO mapToDTO(UserTenant userTenant) {
        return UserTenantDTO.builder()
                .id(userTenant.getId())
                .userId(userTenant.getUser().getId())
                .tenantId(userTenant.getTenant().getId())
                .build();
    }
}

