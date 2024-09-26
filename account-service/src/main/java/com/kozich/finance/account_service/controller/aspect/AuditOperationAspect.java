package com.kozich.finance.account_service.controller.aspect;

import com.kozich.finance.account_service.controller.feign.client.AuditFeignClient;
import com.kozich.finance.account_service.entity.OperationEntity;
import com.kozich.finance.account_service.util.UserHolder;
import com.kozich.finance_storage.core.dto.AuditDTO;
import com.kozich.finance_storage.core.enums.AuditType;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class AuditOperationAspect {

    private final AuditFeignClient auditFeignClient;
    private final UserHolder userHolder;
    private final static String TEXT_CREATE = "Создание операции";
    private final static String TEXT_GET_ALL = "Получение списка операций";
    private final static String TEXT_UPDATE = "Обновление операции";
    private final static String TEXT_DELETE = "Удаление операции";

    public AuditOperationAspect(AuditFeignClient auditFeignClient, UserHolder userHolder) {
        this.auditFeignClient = auditFeignClient;
        this.userHolder = userHolder;
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.OperationServiceImpl.create(..))", returning = "operation")
    public void afterCreate(OperationEntity operation) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), operation.getUuid().toString(), TEXT_CREATE);
        auditFeignClient.create(auditDTO);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.OperationServiceImpl.getPage(..))", returning = "page")
    public void afterGetAll(Page<OperationEntity> page) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), String.valueOf(page.hashCode()), TEXT_GET_ALL);
        auditFeignClient.create(auditDTO);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.OperationServiceImpl.update(..))", returning = "operationEntity")
    public void afterUpdate(OperationEntity operationEntity) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), operationEntity.getUuid().toString(), TEXT_UPDATE);
        auditFeignClient.create(auditDTO);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.OperationServiceImpl.delete(..)) && args(uuid, uuidOperation, dtUpdate)", argNames = "uuid,uuidOperation,dtUpdate")
    public void afterDelete(UUID uuid, UUID uuidOperation, Long dtUpdate) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), uuidOperation.toString(), TEXT_DELETE);
        auditFeignClient.create(auditDTO);
    }

    private AuditDTO getAuditDTO(UUID userId, String id, String text) {

        return new AuditDTO()
                .setUserId(userId)
                .setType(AuditType.USER)
                .setId(id)
                .setText(text);

    }

}