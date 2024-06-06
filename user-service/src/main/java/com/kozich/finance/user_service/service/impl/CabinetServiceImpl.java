package com.kozich.finance.user_service.service.impl;

import com.kozich.finance.user_service.core.MessageStatus;
import com.kozich.finance.user_service.core.UserRole;
import com.kozich.finance.user_service.core.UserStatus;
import com.kozich.finance.user_service.core.dto.MessageDTO;
import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.mapper.UserMapper;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.service.api.CabinetService;
import com.kozich.finance.user_service.service.api.MessageSenderService;
import com.kozich.finance.user_service.service.api.MessageService;
import com.kozich.finance.user_service.service.api.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional(readOnly = true)
public class CabinetServiceImpl implements CabinetService {

    private final UserService userService;
    private final MessageService messageService;

    public CabinetServiceImpl(UserService userService, MessageService messageService1) {
        this.userService = userService;
        this.messageService = messageService1;
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
        int code = random.nextInt(10000);

        MessageDTO messageDTO = new MessageDTO()
                .setStatus(MessageStatus.LOADED)
                .setCode(String.valueOf(code));

        messageService.create(messageDTO);

        return userEntity;
    }
}
