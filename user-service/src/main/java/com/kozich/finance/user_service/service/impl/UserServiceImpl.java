package com.kozich.finance.user_service.service.impl;


import com.kozich.finance.user_service.core.dto.UserCUDTO;
import com.kozich.finance.user_service.mapper.UserMapper;
import com.kozich.finance.user_service.service.api.UserService;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public UserEntity create(UserCUDTO userDTO) {

        LocalDateTime date = LocalDateTime.now();

        UserEntity userEntity = userMapper.userCUDTOToUserEntity(userDTO)
                .setUuid(UUID.randomUUID())
                .setDtCreate(date)
                .setDtUpdate(date);

        return userRepository.saveAndFlush(userEntity);
    }

    @Transactional
    @Override
    public UserEntity update(UUID uuid, UserCUDTO userCUDTO, Long dtUpdate) {

        Optional<UserEntity> userEntity = userRepository.findById(uuid);

        if(userEntity.isEmpty()){
            throw new IllegalArgumentException("Не существует такого пользователя");
        }

        LocalDateTime localDateTime = Instant.ofEpochMilli(dtUpdate).atZone(ZoneId.systemDefault()).toLocalDateTime();

        UserEntity resEntity = userEntity.get()
                .setEmail(userCUDTO.getEmail())
                .setFio(userCUDTO.getFio())
                .setRole(userCUDTO.getRole())
                .setStatus(userCUDTO.getStatus())
                .setPassword(userCUDTO.getPassword())
                .setDtUpdate(localDateTime);

        return userRepository.saveAndFlush(resEntity);
    }

}
