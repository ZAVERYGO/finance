package com.kozich.finance.user_service.service.impl;

import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.mapper.UserMapper;
import com.kozich.finance.user_service.service.api.UserService;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.encoder = encoder;
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
    public UserEntity getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow
                (
                    () -> new IllegalArgumentException("Пользователя не существует")
                );
    }

    @Override
    public Page<UserEntity> getPage(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public UserEntity create(UserDTO userDTO) {

        LocalDateTime date = LocalDateTime.now();

        UserEntity userEntity = userMapper.userDTOToUserEntity(userDTO)
                .setUuid(UUID.randomUUID())
                .setDtCreate(date)
                .setPassword(encoder.encode(userDTO.getPassword()))
                .setDtUpdate(date);

        return userRepository.saveAndFlush(userEntity);
    }

    @Transactional
    @Override
    public UserEntity update(UUID uuid, UserDTO userDTO, Long dtUpdate) {

        Optional<UserEntity> userEntity = userRepository.findById(uuid);

        if(userEntity.isEmpty()){
            throw new IllegalArgumentException("Не существует такого пользователя");
        }

        LocalDateTime localDateTime = Instant.ofEpochMilli(dtUpdate).atZone(ZoneId.systemDefault()).toLocalDateTime();

        UserEntity resEntity = userEntity.get()
                .setEmail(userDTO.getEmail())
                .setFio(userDTO.getFio())
                .setRole(userDTO.getRole())
                .setStatus(userDTO.getStatus())
                .setPassword(userDTO.getPassword())
                .setDtUpdate(localDateTime);

        return userRepository.saveAndFlush(resEntity);
    }

    @Override
    public void delete(UUID uuid) {
        userRepository.deleteById(uuid);
    }

}
