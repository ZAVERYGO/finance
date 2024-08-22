package com.kozich.finance.user_service.feign.aspect;

import com.kozich.finance.user_service.controller.utils.JwtTokenHandler;
import com.kozich.finance.user_service.core.enums.AuditType;
import com.kozich.finance.user_service.core.dto.AuditCUDTO;
import com.kozich.finance.user_service.core.dto.UserAuditDTO;
import com.kozich.finance.user_service.feign.client.AuditFeignClient;
import com.kozich.finance.user_service.entity.UserEntity;
import com.kozich.finance.user_service.security.UserHolder;
import com.kozich.finance.user_service.service.api.UserService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditCabinetAspect {

    private final AuditFeignClient auditFeignClient;
    private final UserHolder userHolder;
    private final UserService userService;
    private final JwtTokenHandler jwtTokenHandler;

    private final static String TEXT_REGISTRATION = "Регистрация пользователя";
    private final static String TEXT_VERIFICATION = "Верификация пользователя";
    private final static String TEXT_LOGIN = "Вход пользователя в приложение";
    private final static String TEXT_MY_CABINET = "Получение пользователем информации о себе";

    public AuditCabinetAspect(AuditFeignClient auditFeignClient, UserHolder userHolder,
                              UserService userService, JwtTokenHandler jwtTokenHandler) {
        this.auditFeignClient = auditFeignClient;
        this.userHolder = userHolder;
        this.userService = userService;
        this.jwtTokenHandler = jwtTokenHandler;
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.service.impl.CabinetServiceImpl.registerUser(..))", returning = "userEntity")
    public void afterRegistration(UserEntity userEntity) {
        UserAuditDTO userAuditDTO = getUserAudit(userEntity);
        AuditCUDTO audit = getAuditCUDTO(TEXT_REGISTRATION, userAuditDTO, userAuditDTO.getUuid().toString());
        auditFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.service.impl.CabinetServiceImpl.verifyUser(..))", returning = "userEntity")
    public void afterVerify(UserEntity userEntity) {
        UserAuditDTO userAuditDTO = getUserAudit(userEntity);
        AuditCUDTO audit = getAuditCUDTO(TEXT_VERIFICATION, userAuditDTO, userAuditDTO.getUuid().toString());
        auditFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.service.impl.CabinetServiceImpl.loginUser(..))", returning = "token")
    public void afterLogin(String token) {
        UserEntity byEmail = userService.getByEmail(jwtTokenHandler.getUsername(token));
        UserAuditDTO userAuditDTO = getUserAudit(byEmail);
        AuditCUDTO audit = getAuditCUDTO(TEXT_LOGIN, userAuditDTO, byEmail.getUuid().toString());
        auditFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.service.impl.CabinetServiceImpl.getMyCabinet(..))")
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
