package com.kozich.finance.user_service.service.impl;

import com.kozich.finance.user_service.core.MessageStatus;
import com.kozich.finance.user_service.core.UserRole;
import com.kozich.finance.user_service.core.UserStatus;
import com.kozich.finance.user_service.core.dto.MessageDTO;
import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.mapper.UserMapper;
import com.kozich.finance.user_service.model.MessageEntity;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.service.api.CabinetService;
import com.kozich.finance.user_service.service.api.MessageService;
import com.kozich.finance.user_service.service.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class CabinetServiceImpl implements CabinetService {

    private final UserService userService;
    private final MessageService messageService;
    private final UserMapper userMapper;

    public CabinetServiceImpl(UserService userService, MessageService messageService1, UserMapper userMapper) {
        this.userService = userService;
        this.messageService = messageService1;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public UserEntity registerUser(UserDTO userDTO) {

        UserEntity byEmail = userService.getByEmail(userDTO.getEmail());

        if(byEmail != null){
            throw new IllegalArgumentException("Пользователь уже существует");
        }

        UserEntity userEntity = userService.create(userDTO
                .setRole(UserRole.ROLE_USER)
                .setStatus(UserStatus.WAITING_ACTIVATION));

        Random random = new Random();
        int code = random.nextInt(1000000);

        MessageDTO messageDTO = new MessageDTO()
                .setStatus(MessageStatus.LOADED)
                .setCode(String.valueOf(code));

        messageService.create(messageDTO);

        return userEntity;
    }

    @Transactional
    @Override
    public void verifyUser(String code, String mail) {

        UserEntity userEntity = userService.getByEmail(mail);

        MessageEntity messageEntity = messageService.getByUser(userEntity);

        UserDTO userDTO = userMapper.userEntityToUserDTO(userEntity);

        if(messageEntity.getCode().equals(code)){

            userDTO.setStatus(UserStatus.ACTIVATED);

            userService.update(userEntity.getUuid(), userDTO, Instant.now().getEpochSecond());

        }else {
            throw new IllegalArgumentException("Неверный код верификации");
        }


    }
}
