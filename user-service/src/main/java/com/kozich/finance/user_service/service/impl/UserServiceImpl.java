package com.kozich.finance.user_service.service.impl;


import com.kozich.finance.user_service.service.api.UserService;
import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.repository.UserRepository;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserEntity get(UUID UUID, String page, String size) {

        return null;
    }

    @Override
    public UserEntity getPage(UUID UUID) {
        return null;
    }

    @Override
    public UserEntity create(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserEntity update(UserDTO userDTO) {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }
}
