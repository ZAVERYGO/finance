package com.kozich.finance.account_service.controller.feign.aspect;

import com.kozich.finance.account_service.controller.feign.client.AuditFeignClient;
import com.kozich.finance.account_service.controller.feign.client.UserFeignClient;
import com.kozich.finance.account_service.core.enums.AuditType;
import com.kozich.finance.account_service.core.dto.AuditCUDTO;
import com.kozich.finance.account_service.core.dto.UserAuditDTO;
import com.kozich.finance.account_service.core.dto.UserDTO;
import com.kozich.finance.account_service.entity.AccountEntity;
import com.kozich.finance.account_service.util.UserHolder;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAccountAspect {

    private final AuditFeignClient auditFeign;
    private final UserFeignClient userFeignClient;
    private final UserHolder userHolder;
    private final static String TEXT_CREATE = "Создание аккаунта";
    private final static String TEXT_GET_BY_ID = "Получение аккаунта по id";
    private final static String TEXT_GET_ALL = "Получение списка аккаунтов";
    private final static String TEXT_UPDATE = "Обновление аккаунта";

    public AuditAccountAspect(AuditFeignClient auditFeign, UserFeignClient userFeignClient, UserHolder userHolder) {
        this.auditFeign = auditFeign;
        this.userFeignClient = userFeignClient;
        this.userHolder = userHolder;
    }


    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.AccountServiceImpl.create(..))", returning = "account")
    public void afterCreate(AccountEntity account) {
        UserAuditDTO userAuditDTO = getUserAudit();
        AuditCUDTO audit = getAuditCUDTO(TEXT_CREATE, userAuditDTO, account.getUuid().toString());
        this.auditFeign.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.AccountServiceImpl.getById(..))", returning = "account")
    public void afterGetById(AccountEntity account) {
        UserAuditDTO userAuditDTO = getUserAudit();
        AuditCUDTO audit = getAuditCUDTO(TEXT_GET_BY_ID, userAuditDTO, account.getUuid().toString());
        this.auditFeign.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.AccountServiceImpl.getPage(..))", returning="page")
    public void afterGetAll(Page<AccountEntity> page) {
        UserAuditDTO userAuditDTO = getUserAudit();
        AuditCUDTO audit = getAuditCUDTO(TEXT_GET_ALL, userAuditDTO, String.valueOf(page.hashCode()));
        this.auditFeign.create(audit);
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.account_service.service.impl.AccountServiceImpl.update(..))", returning="accountEntity")
    public void afterUpdate(AccountEntity accountEntity) {
        UserAuditDTO userAuditDTO = getUserAudit();
        AuditCUDTO audit = getAuditCUDTO(TEXT_UPDATE, userAuditDTO, accountEntity.getUuid().toString());
        this.auditFeign.create(audit);
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
                .setType(AuditType.ACCOUNT)
                .setId(id)
                .setText(text);

        return auditCUDTO;
    }
}


