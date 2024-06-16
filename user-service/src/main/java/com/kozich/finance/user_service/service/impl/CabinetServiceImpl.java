package com.kozich.finance.user_service.service.impl;

import com.kozich.finance.user_service.controller.utils.JwtTokenHandler;
import com.kozich.finance.user_service.core.MessageStatus;
import com.kozich.finance.user_service.core.UserRole;
import com.kozich.finance.user_service.core.UserStatus;
import com.kozich.finance.user_service.core.dto.*;
import com.kozich.finance.user_service.mapper.UserMapper;
import com.kozich.finance.user_service.model.MessageEntity;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.model.MyUserDetails;
import com.kozich.finance.user_service.service.UserHolder;
import com.kozich.finance.user_service.service.api.CabinetService;
import com.kozich.finance.user_service.service.api.MessageService;
import com.kozich.finance.user_service.service.api.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.util.Random;

@Service
@Transactional(readOnly = true)
public class CabinetServiceImpl implements CabinetService {

    private final UserService userService;
    private final MessageService messageService;
    private final UserHolder userHolder;
    private final PasswordEncoder encoder;
    private final JwtTokenHandler jwtHandler;

    public CabinetServiceImpl(UserService userService, MessageService messageService1, UserHolder userHolder,
                              PasswordEncoder encoder, JwtTokenHandler jwtHandler) {
        this.userService = userService;
        this.messageService = messageService1;
        this.userHolder = userHolder;
        this.encoder = encoder;
        this.jwtHandler = jwtHandler;
    }

    @Transactional
    @Override
    public UserEntity registerUser(RegistrationDTO registrationDTO) {

        if(userService.existsByEmail(registrationDTO.getEmail())){
            throw new IllegalArgumentException("Логин уже занят. Введите другой");
        }

        UserCUDTO userCDTO = new UserCUDTO()
                .setEmail(registrationDTO.getEmail())
                .setPassword(registrationDTO.getPassword())
                .setFio(registrationDTO.getFio());

        UserEntity userEntity = userService.create(userCDTO
                .setRole(UserRole.ROLE_USER)
                .setStatus(UserStatus.WAITING_ACTIVATION));

        Random random = new Random();
        int code = random.nextInt(1000000);

        MessageDTO messageDTO = new MessageDTO()
                .setEmail(userEntity.getEmail())
                .setStatus(MessageStatus.LOADED)
                .setCode(String.valueOf(code));

        messageService.create(messageDTO);

        return userEntity;
    }

    @Transactional
    @Override
    public UserEntity verifyUser(String code, String mail) {

        UserEntity userEntity = userService.getByEmail(mail);

        if(userEntity.getStatus().equals(UserStatus.ACTIVATED)){
            throw new IllegalArgumentException("Пользователь уже верифицирован");
        }
        MessageEntity messageEntity = messageService.getByUser(userEntity);

        if(messageEntity.getCode().equals(code)){

            UserCUDTO userDTO = new UserCUDTO()
                    .setEmail(userEntity.getEmail())
                    .setStatus(UserStatus.ACTIVATED)
                    .setRole(UserRole.ROLE_USER)
                    .setFio(userEntity.getFio());

            long epochMilli = userEntity.getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli();

            UserEntity update = userService.update(userEntity.getUuid(), userDTO, epochMilli);

            return update;
        }else {
            throw new IllegalArgumentException("Неверный код верификации");
        }
    }

    @Override
    public String loginUser(LoginDTO loginDTO) {

        String email = loginDTO.getEmail();
        UserEntity userEntity;
        if(userService.existsByEmail(email)){
            userEntity = userService.getByEmail(email);
            if(!userEntity.getStatus().equals(UserStatus.ACTIVATED)){
                throw new IllegalArgumentException("Вы не прошли верификацию");
            }
        }else{
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        String passwordDTO = loginDTO.getPassword();
        String passwordEntity = userEntity.getPassword();
        if(!encoder.matches(passwordDTO, passwordEntity)){
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        UserDetails userDetails = new MyUserDetails(userEntity);

        return jwtHandler.generateAccessToken(userDetails);
    }

    @Override
    public UserEntity getMyCabinet() {
        return userService.getByEmail(userHolder.getUser().getUsername());
    }
}
