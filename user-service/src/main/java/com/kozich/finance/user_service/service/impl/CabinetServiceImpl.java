package com.kozich.finance.user_service.service.impl;

import com.kozich.finance.user_service.core.dto.LoginDTO;
import com.kozich.finance.user_service.core.dto.MessageDTO;
import com.kozich.finance.user_service.core.dto.RegistrationDTO;
import com.kozich.finance.user_service.core.dto.UserCUDTO;
import com.kozich.finance.user_service.core.enums.MessageStatus;
import com.kozich.finance.user_service.core.enums.UserRole;
import com.kozich.finance.user_service.core.enums.UserStatus;
import com.kozich.finance.user_service.entity.MessageEntity;
import com.kozich.finance.user_service.entity.UserEntity;
import com.kozich.finance.user_service.service.api.CabinetService;
import com.kozich.finance.user_service.service.api.MessageService;
import com.kozich.finance.user_service.service.api.UserService;
import com.kozich.finance.user_service.util.JwtTokenHandler;
import com.kozich.finance.user_service.util.MyUserDetails;
import com.kozich.finance.user_service.util.UserHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
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

        if (userService.existsByEmail(registrationDTO.getEmail())) {
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

        if (userEntity.getStatus().equals(UserStatus.ACTIVATED)) {
            throw new IllegalArgumentException("Пользователь уже верифицирован");
        }

        MessageEntity messageEntity = messageService.getByUser(userEntity);

        if (messageEntity.getCode().equals(code)) {
            UserCUDTO userDTO = new UserCUDTO()
                    .setEmail(userEntity.getEmail())
                    .setStatus(UserStatus.ACTIVATED)
                    .setRole(UserRole.ROLE_USER)
                    .setFio(userEntity.getFio());

            long epochSecond = userEntity.getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();

            return userService.update(userEntity.getUuid(), userDTO, epochSecond);
        } else {
            throw new IllegalArgumentException("Неверный код верификации");
        }
    }

    @Override
    public String loginUser(LoginDTO loginDTO) {

        String email = loginDTO.getEmail();
        UserEntity userEntity;

        if (userService.existsByEmail(email)) {
            userEntity = userService.getByEmail(email);
            if (!userEntity.getStatus().equals(UserStatus.ACTIVATED)) {
                throw new IllegalArgumentException("Вы не прошли верификацию");
            }
        } else {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        String passwordDTO = loginDTO.getPassword();
        String passwordEntity = userEntity.getPassword();
        if (!encoder.matches(passwordDTO, passwordEntity)) {
            throw new IllegalArgumentException("Неверный логин или пароль");
        }

        return jwtHandler.generateAccessToken(new MyUserDetails(userEntity));
    }

    @Override
    public UserEntity getMyCabinet() {
        return userService.getByEmail(userHolder.getUser().getUsername());
    }

}
