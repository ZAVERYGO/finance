package com.kozich.finance.user_service.service.impl;

import com.kozich.finance.user_service.core.dto.UserCUDTO;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
        UserEntity userEntity = userRepository.findById(uuid).orElseThrow(
                    () -> new IllegalArgumentException("Пользователя не существует"));
        return userEntity;
    }

    @Override
    public UserEntity getByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("Пользователя не существует"));
        return userEntity;
    }

    @Override
    public Page<UserEntity> getPage(Integer page, Integer size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public UserEntity create(UserCUDTO userCDTO) {

        if(existsByEmail(userCDTO.getEmail())){
            throw new IllegalArgumentException("Пользователь уже существует");
        }
        LocalDateTime date = LocalDateTime.now();

        UserEntity userEntity = new UserEntity()
                .setUuid(UUID.randomUUID())
                .setFio(userCDTO.getFio())
                .setEmail(userCDTO.getEmail())
                .setStatus(userCDTO.getStatus())
                .setRole(userCDTO.getRole())
                .setDtCreate(date)
                .setPassword(encoder.encode(userCDTO.getPassword()))
                .setDtUpdate(date);

        return userRepository.saveAndFlush(userEntity);
    }

    @Transactional
    @Override
    public UserEntity update(UUID uuid, UserDTO userDTO, Long dtUpdate) {

        Optional<UserEntity> userEntity = userRepository.findById(uuid);

        if(userEntity.isEmpty()){
            throw new IllegalArgumentException("Пользователя не существует");
        }
        Long dateTime = userEntity.get().getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli();
        if(!dateTime.equals(dtUpdate)){
            throw new IllegalArgumentException("Пользователь уже был изменен");
        }

        UserEntity resEntity = userEntity.get()
                .setEmail(userDTO.getEmail())
                .setFio(userDTO.getFio())
                .setRole(userDTO.getRole())
                .setStatus(userDTO.getStatus());

        if(userDTO.getPassword() == null) {
            resEntity.setPassword(userEntity.get().getPassword());
        }

        return userRepository.saveAndFlush(resEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void delete(UUID uuid) {
        userRepository.deleteById(uuid);
    }

}
