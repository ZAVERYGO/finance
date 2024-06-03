package com.kozich.finance.user_service.Service.impl;


import com.kozich.finance.user_service.Service.api.UserService;
import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.model.UserEntity;

import java.util.UUID;

public class UserServiceImpl implements UserService {

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
