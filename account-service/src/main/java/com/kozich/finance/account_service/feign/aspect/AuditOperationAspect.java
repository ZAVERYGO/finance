package com.kozich.finance.account_service.feign.aspect;

import com.kozich.finance.account_service.core.AuditType;
import com.kozich.finance.account_service.core.dto.AuditCUDTO;
import com.kozich.finance.account_service.core.dto.UserAuditDTO;
import com.kozich.finance.account_service.core.dto.UserDTO;
import com.kozich.finance.account_service.feign.client.AuditFeignClient;
import com.kozich.finance.account_service.feign.client.UserFeignClient;
import com.kozich.finance.account_service.model.OperationEntity;
import com.kozich.finance.account_service.service.UserHolder;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class AuditOperationAspect {

    private final AuditFeignClient auditFeignClient;
    private final UserFeignClient userFeignClient;
    private final UserHolder userHolder;
    private final static String TEXT_CREATE = "Создание операции";
    private final static String TEXT_GET_ALL = "Получение списка операций";
    private final static String TEXT_UPDATE = "Обновление операции";
    private final static String TEXT_DELETE = "Удаление операции";

    public AuditOperationAspect(AuditFeignClient auditFeignClient, UserFeignClient userFeignClient,
                                UserHolder userHolder) {

        this.auditFeignClient = auditFeignClient;
        this.userFeignClient = userFeignClient;
        this.userHolder = userHolder;
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.OperationServiceImpl.create(..))", returning = "operation")
    public void afterCreate(OperationEntity operation) {

        UserAuditDTO userAuditDTO = getUserAudit();

        AuditCUDTO audit = getAuditCUDTO(TEXT_CREATE , userAuditDTO, operation.getUuid().toString());

        auditFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.OperationServiceImpl.getPage(..))", returning = "page")
    public void afterGetAll(Page<OperationEntity> page) {

        UserAuditDTO userAuditDTO = getUserAudit();

        AuditCUDTO audit = getAuditCUDTO(TEXT_GET_ALL, userAuditDTO, String.valueOf(page.hashCode()));

        auditFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.OperationServiceImpl.update(..))", returning = "operationEntity")
    public void afterUpdate(OperationEntity operationEntity) {

        UserAuditDTO userAuditDTO = getUserAudit();

        AuditCUDTO audit = getAuditCUDTO(TEXT_UPDATE, userAuditDTO, operationEntity.getUuid().toString());

        auditFeignClient.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.OperationServiceImpl.delete(..)) && args(uuid, uuidOperation, dtUpdate)", argNames = "uuid,uuidOperation,dtUpdate")
    public void afterDelete(UUID uuid, UUID uuidOperation, Long dtUpdate) {

        UserAuditDTO userAuditDTO = getUserAudit();

        AuditCUDTO audit = getAuditCUDTO(TEXT_DELETE, userAuditDTO, uuidOperation.toString());

        auditFeignClient.create(audit);
    }

    private UserAuditDTO getUserAudit() {

        UserDTO userByEmail = userFeignClient.getUserByEmail(
                userHolder.getUser().getUsername());

        UserAuditDTO userAuditDTO = new UserAuditDTO()
                .setMail(userByEmail.getEmail())
                .setUuid(userByEmail.getUuid())
                .setFio(userByEmail.getFio())
                .setRole(userByEmail.getRole());

        return userAuditDTO;
    }


    private AuditCUDTO getAuditCUDTO(String text, UserAuditDTO user, String id) {

        AuditCUDTO auditCUDTO = new AuditCUDTO()
                .setUser(user)
                .setType(AuditType.OPERATION)
                .setId(id)
                .setText(text);

        return auditCUDTO;
    }
}