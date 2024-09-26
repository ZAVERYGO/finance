package com.kozich.finance.account_service.controller.aspect;

import com.kozich.finance.account_service.controller.feign.client.AuditFeignClient;
import com.kozich.finance.account_service.entity.AccountEntity;
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
public class AuditAccountAspect {

    private final AuditFeignClient auditFeignClient;
    private final UserHolder userHolder;
    private final static String TEXT_CREATE = "Создание аккаунта";
    private final static String TEXT_GET_BY_ID = "Получение аккаунта по id";
    private final static String TEXT_GET_ALL = "Получение списка аккаунтов";
    private final static String TEXT_UPDATE = "Обновление аккаунта";

    public AuditAccountAspect(AuditFeignClient auditFeignClient, UserHolder userHolder) {
        this.auditFeignClient = auditFeignClient;
        this.userHolder = userHolder;
    }


    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.AccountServiceImpl.create(..))", returning = "account")
    public void afterCreate(AccountEntity account) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), account.getUuid().toString(), TEXT_CREATE);
        auditFeignClient.create(auditDTO);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.AccountServiceImpl.getById(..))", returning = "account")
    public void afterGetById(AccountEntity account) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), account.getUuid().toString(), TEXT_GET_BY_ID);
        auditFeignClient.create(auditDTO);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.AccountServiceImpl.getPage(..))", returning = "page")
    public void afterGetAll(Page<AccountEntity> page) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), String.valueOf(page.hashCode()), TEXT_GET_ALL);
        auditFeignClient.create(auditDTO);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.AccountServiceImpl.update(..))", returning = "accountEntity")
    public void afterUpdate(AccountEntity accountEntity) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), accountEntity.getUuid().toString(), TEXT_UPDATE);
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


