package com.kozich.finance.user_service.service.api;

import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.model.UserEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

    UserEntity getById(UUID uuid);

    UserEntity getByEmail(String email);

    Page<UserEntity> getPage(Integer page, Integer size);

    UserEntity create(UserDTO userDTO);

    UserEntity update(UUID uuid, UserDTO userDTO, Long dtUpdate);

    void delete(UUID uuid);

}
