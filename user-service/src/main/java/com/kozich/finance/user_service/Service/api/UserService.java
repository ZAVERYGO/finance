package com.kozich.finance.user_service.Service.api;

import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.model.UserEntity;

import java.util.UUID;

public interface UserService {

    UserEntity get(UUID UUID, String page, String size);

    UserEntity getPage(UUID UUID);

    UserEntity create(UserDTO userDTO);

    UserEntity update(UserDTO userDTO);

    void delete(UUID uuid);
}
