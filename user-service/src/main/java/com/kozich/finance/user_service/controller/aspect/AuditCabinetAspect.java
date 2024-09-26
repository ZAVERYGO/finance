package com.kozich.finance.user_service.controller.aspect;

import com.kozich.finance.user_service.controller.feign.client.AuditFeignClient;
import com.kozich.finance.user_service.core.dto.LoginDTO;
import com.kozich.finance.user_service.core.dto.RegistrationDTO;
import com.kozich.finance.user_service.entity.UserEntity;
import com.kozich.finance.user_service.service.api.UserService;
import com.kozich.finance.user_service.util.UserHolder;
import com.kozich.finance_storage.core.dto.AuditDTO;
import com.kozich.finance_storage.core.enums.AuditType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class AuditCabinetAspect {

    private final AuditFeignClient auditFeignClient;
    private final UserHolder userHolder;
    private final UserService userService;

    private final static String TEXT_REGISTRATION = "Регистрация пользователя";
    private final static String TEXT_VERIFICATION = "Верификация пользователя";
    private final static String TEXT_LOGIN = "Вход пользователя в приложение";
    private final static String TEXT_MY_CABINET = "Получение пользователем информации о себе";

    public AuditCabinetAspect(AuditFeignClient auditFeignClient, UserHolder userHolder,
                              UserService userService) {
        this.auditFeignClient = auditFeignClient;
        this.userHolder = userHolder;
        this.userService = userService;
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.CabinetController.registerUser(..))")
    public void afterRegistration(JoinPoint joinPoint) {
        RegistrationDTO registrationDTO = (RegistrationDTO) joinPoint.getArgs()[0];
        UserEntity userEntity = userService.getByEmail(registrationDTO.getEmail());
        auditFeignClient.create(getAuditDTO(userEntity.getUuid(), userEntity.getUuid().toString(), TEXT_REGISTRATION));
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.CabinetController.verifyUser(..))")
    public void afterVerify(JoinPoint joinPoint) {
        String email = (String) joinPoint.getArgs()[1];
        UserEntity userEntity = userService.getByEmail(email);
        auditFeignClient.create(getAuditDTO(userEntity.getUuid(), userEntity.getUuid().toString(), TEXT_VERIFICATION));
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.CabinetController.loginUser(..))")
    public void afterLogin(JoinPoint joinPoint) {
        LoginDTO loginDTO = (LoginDTO) joinPoint.getArgs()[0];
        UserEntity userEntity = userService.getByEmail(loginDTO.getEmail());
        auditFeignClient.create(getAuditDTO(userEntity.getUuid(), userEntity.getUuid().toString(), TEXT_LOGIN));
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.CabinetController.getMyCabinet(..))")
    public void afterGetCabinet() {
        UUID userId = userHolder.getUser().getUsername();
        auditFeignClient.create(getAuditDTO(userId, userId.toString(), TEXT_MY_CABINET));
    }


    private AuditDTO getAuditDTO(UUID userId, String id, String text) {

        return new AuditDTO()
                .setUserId(userId)
                .setType(AuditType.USER)
                .setId(id)
                .setText(text);

    }

}
