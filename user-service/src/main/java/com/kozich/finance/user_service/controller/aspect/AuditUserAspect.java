package com.kozich.finance.user_service.controller.aspect;

import com.kozich.finance.user_service.controller.feign.client.AuditFeignClient;
import com.kozich.finance.user_service.core.dto.UserCUDTO;
import com.kozich.finance.user_service.core.dto.UserDTO;
import com.kozich.finance.user_service.entity.UserEntity;
import com.kozich.finance.user_service.service.api.UserService;
import com.kozich.finance.user_service.util.UserHolder;
import com.kozich.finance_storage.core.dto.AuditDTO;
import com.kozich.finance_storage.core.enums.AuditType;
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
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), userEntity.getUuid().toString(), TEXT_CREATE);
        this.auditServiceFeignClient.create(auditDTO);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.UserController.getPage(..))", returning = "page")
    public void afterGetPage(Page<UserEntity> page) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), page.toString(), TEXT_GET_ALL);
        this.auditServiceFeignClient.create(auditDTO);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.UserController.getById(..))", returning = "user")
    public void afterGetById(UserDTO user) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), user.getUuid().toString(), TEXT_GET_BY_ID);
        this.auditServiceFeignClient.create(auditDTO);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.user_service.controller.http.UserController.update(..))")
    public void afterUpdate(JoinPoint joinPoint) {
        UUID uuid = (UUID) joinPoint.getArgs()[1];
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), uuid.toString(), TEXT_UPDATE);
        this.auditServiceFeignClient.create(auditDTO);
    }

    private AuditDTO getAuditDTO(UUID userId, String id, String text) {

        return new AuditDTO()
                .setUserId(userId)
                .setType(AuditType.USER)
                .setId(id)
                .setText(text);

    }

}
