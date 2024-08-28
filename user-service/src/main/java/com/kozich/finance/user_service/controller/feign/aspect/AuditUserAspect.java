package com.kozich.finance.user_service.controller.feign.aspect;

import com.kozich.finance.user_service.controller.feign.client.AuditFeignClient;
import com.kozich.finance.user_service.core.dto.AuditCUDTO;
import com.kozich.finance.user_service.core.dto.UserAuditDTO;
import com.kozich.finance.user_service.core.dto.UserCUDTO;
import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.core.enums.AuditType;
import com.kozich.finance.user_service.entity.UserEntity;
import com.kozich.finance.user_service.service.api.UserService;
import com.kozich.finance.user_service.util.UserHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class AuditUserAspect {

    private final AuditFeignClient auditServiceFeignClient;
    private final UserHolder userHolder;
    private final UserService userService;

    private static final String TEXT_CREATE = "Создание пользователем нового пользователя";
    private static final String TEXT_GET_ALL = "Получение пользователем списка пользователей";
    private static final String TEXT_GET_BY_ID = "Получение пользователем другого пользователя";
    private static final String TEXT_UPDATE = "Пользователь обновил другого пользователя";

    public AuditUserAspect(AuditFeignClient auditServiceFeignClient,
                           UserHolder userHolder, UserService userService) {
        this.auditServiceFeignClient = auditServiceFeignClient;
        this.userHolder = userHolder;
        this.userService = userService;
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.UserController.create(..))")
    public void afterCreate(JoinPoint joinPoint) {
        UserCUDTO userCUDTO = (UserCUDTO) joinPoint.getArgs()[0];
        UserEntity userEntity = userService.getByEmail(userCUDTO.getEmail());
        UserAuditDTO userAuditDTO = getUserAudit();
        AuditCUDTO audit = getAuditCUDTO(TEXT_CREATE, userAuditDTO, userEntity.getUuid().toString());
        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.UserController.getPage(..))", returning = "page")
    public void afterGetPage(Page<UserEntity> page) {
        UserAuditDTO userAuditDTO = getUserAudit();
        AuditCUDTO audit = getAuditCUDTO(TEXT_GET_ALL, userAuditDTO, String.valueOf(page.hashCode()));
        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.UserController.getById(..))", returning = "user")
    public void afterGetById(UserDTO user) {
        UserAuditDTO userAuditDTO = getUserAudit();
        AuditCUDTO audit = getAuditCUDTO(TEXT_GET_BY_ID, userAuditDTO, user.getUuid().toString());
        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.UserController.update(..))")
    public void afterUpdate(JoinPoint joinPoint) {
        UUID uuid = (UUID) joinPoint.getArgs()[1];
        UserAuditDTO userAuditDTO = getUserAudit();
        AuditCUDTO audit = getAuditCUDTO(TEXT_UPDATE, userAuditDTO, uuid.toString());
        this.auditServiceFeignClient.create(audit);
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

    private AuditCUDTO getAuditCUDTO(String text, UserAuditDTO user, String id) {

        AuditCUDTO auditCUDTO = new AuditCUDTO()
                .setUser(user)
                .setType(AuditType.USER)
                .setId(id)
                .setText(text);

        return auditCUDTO;
    }

}
