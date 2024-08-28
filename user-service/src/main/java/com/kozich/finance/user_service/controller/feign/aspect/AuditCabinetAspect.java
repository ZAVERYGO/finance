package com.kozich.finance.user_service.controller.feign.aspect;

import com.kozich.finance.user_service.controller.feign.client.AuditFeignClient;
import com.kozich.finance.user_service.core.dto.AuditCUDTO;
import com.kozich.finance.user_service.core.dto.LoginDTO;
import com.kozich.finance.user_service.core.dto.RegistrationDTO;
import com.kozich.finance.user_service.core.dto.UserAuditDTO;
import com.kozich.finance.user_service.core.enums.AuditType;
import com.kozich.finance.user_service.entity.UserEntity;
import com.kozich.finance.user_service.service.api.UserService;
import com.kozich.finance.user_service.util.UserHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

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
        UserAuditDTO userAuditDTO = getUserAudit(userEntity);
        AuditCUDTO audit = getAuditCUDTO(TEXT_REGISTRATION, userAuditDTO, userAuditDTO.getUuid().toString());
        auditFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.CabinetController.verifyUser(..))")
    public void afterVerify(JoinPoint joinPoint) {
        String email = (String) joinPoint.getArgs()[1];
        UserEntity userEntity = userService.getByEmail(email);
        UserAuditDTO userAuditDTO = getUserAudit(userEntity);
        AuditCUDTO audit = getAuditCUDTO(TEXT_VERIFICATION, userAuditDTO, userAuditDTO.getUuid().toString());
        auditFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.CabinetController.loginUser(..))")
    public void afterLogin(JoinPoint joinPoint) {
        LoginDTO loginDTO = (LoginDTO) joinPoint.getArgs()[0];
        UserEntity userEntity = userService.getByEmail(loginDTO.getEmail());
        UserAuditDTO userAuditDTO = getUserAudit(userEntity);
        AuditCUDTO audit = getAuditCUDTO(TEXT_LOGIN, userAuditDTO, userAuditDTO.getUuid().toString());
        auditFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.CabinetController.getMyCabinet(..))")
    public void afterGetCabinet() {
        UserAuditDTO userAuditDTO = getUserAudit();
        AuditCUDTO audit = getAuditCUDTO(TEXT_MY_CABINET, userAuditDTO, userAuditDTO.getUuid().toString());
        auditFeignClient.create(audit);
    }


    private UserAuditDTO getUserAudit() {

        UserEntity userEntity = userService.getByEmail(userHolder.getUser().getUsername());

        UserAuditDTO userAuditDTO = new UserAuditDTO()
                .setUuid(userEntity.getUuid())
                .setRole(userEntity.getRole())
                .setMail(userEntity.getEmail())
                .setFio(userEntity.getFio());

        return userAuditDTO;
    }

    private UserAuditDTO getUserAudit(UserEntity userEntity) {

        UserAuditDTO userAuditDTO = new UserAuditDTO()
                .setUuid(userEntity.getUuid())
                .setRole(userEntity.getRole())
                .setMail(userEntity.getEmail())
                .setFio(userEntity.getFio());

        return userAuditDTO;
    }

    private AuditCUDTO getAuditCUDTO(String text, UserAuditDTO user, String id) {

        AuditCUDTO auditCUDTO = new AuditCUDTO()
                .setUser(user)
                .setType(AuditType.USER)
                .setId(id)
                .setText(text);

        return auditCUDTO;
    }

}
