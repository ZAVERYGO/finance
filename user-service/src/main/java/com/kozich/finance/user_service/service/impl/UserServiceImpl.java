package com.kozich.finance.user_service.service.impl;


import com.kozich.finance.user_service.mapper.UserMapper;
import com.kozich.finance.user_service.service.api.UserService;
import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserEntity getById(UUID uuid) {
        UserEntity userEntity = userRepository.findById(uuid).orElseThrow
                (
                    () -> new IllegalArgumentException("Пользователя не существует")
                );
        return userEntity;
    }

    @Override
    public Page<UserEntity> getPage(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public UserEntity create(UserDTO userDTO) {
        return null;
    }

    @Transactional
    @Override
    public UserEntity update(UserDTO userDTO) {
        return null;
    }

    @Transactional
    @Override
    public void delete(UUID uuid) {

    }
}
