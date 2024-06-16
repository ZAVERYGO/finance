package com.kozich.finance.user_service.feign.aspect;

import com.kozich.finance.user_service.core.AuditType;
import com.kozich.finance.user_service.core.dto.AuditCUDTO;
import com.kozich.finance.user_service.core.dto.UserAuditDTO;
import com.kozich.finance.user_service.feign.client.AuditFeignClient;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.service.UserHolder;
import com.kozich.finance.user_service.service.api.UserService;
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

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.service.impl.UserServiceImpl.create(..))", returning = "entity")
    public void afterCreate(JoinPoint joinPoint, UserEntity entity) {

        String className = joinPoint.getSignature().getDeclaringTypeName();
        if (!className.equals("com.kozich.finance.user_service.controller.http.UserController")) {
            return;
        }

        UserAuditDTO userAuditDTO = getUserAudit();

        AuditCUDTO audit = getAuditCUDTO(TEXT_CREATE, userAuditDTO, entity.getUuid().toString());

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.service.impl.UserServiceImpl.getPage(..))", returning = "page")
    public void afterGetAll(JoinPoint joinPoint, Page<UserEntity> page) {

        String className = joinPoint.getSignature().getDeclaringTypeName();
        if (!className.equals("com.kozich.finance.user_service.controller.http.UserController")) {
            return;
        }

        UserAuditDTO userAuditDTO = getUserAudit();

        AuditCUDTO audit = getAuditCUDTO(TEXT_GET_ALL, userAuditDTO, String.valueOf(page.hashCode()));

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.service.impl.UserServiceImpl.getById(..))", returning = "user")
    public void afterGetById(JoinPoint joinPoint, UserEntity user) {

        String className = joinPoint.getSignature().getDeclaringTypeName();
        if (!className.equals("com.kozich.finance.user_service.controller.http.UserController")) {
            return;
        }

        UserAuditDTO userAuditDTO = getUserAudit();

        AuditCUDTO audit = getAuditCUDTO(TEXT_GET_BY_ID, userAuditDTO, user.getUuid().toString());

        this.auditServiceFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.service.impl.UserServiceImpl.update(..))", returning = "user")
    public void afterUpdate(JoinPoint joinPoint, UserEntity user) {

        String className = joinPoint.getSignature().getDeclaringTypeName();
        if (!className.equals("com.kozich.finance.user_service.controller.http.UserController")) {
            return;
        }

        UserAuditDTO userAuditDTO = getUserAudit();

        AuditCUDTO audit = getAuditCUDTO(TEXT_UPDATE, userAuditDTO, user.getUuid().toString());

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