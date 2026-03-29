package org.martpilot.service;

import org.martpilot.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO create(UserDTO userDTO);

    UserDTO getById(Long id);

    UserDTO getByPhone(String phone);

    List<UserDTO> getAll();

    UserDTO update(Long id, UserDTO userDTO);

    void delete(Long id);
}

