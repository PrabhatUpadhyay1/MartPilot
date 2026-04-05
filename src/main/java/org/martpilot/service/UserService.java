package org.martpilot.service;

import org.martpilot.dto.UpdateUserRequest;
import org.martpilot.dto.UserRequest;
import org.martpilot.dto.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers(Long tenantId);
    UserResponse getUserById(Long userId, Long tenantId);
    UserResponse createUser(UserRequest request, Long tenantId);
    UserResponse updateUser(Long userId, UpdateUserRequest request, Long tenantId);
    void softDeleteUser(Long userId, Long tenantId);
}

