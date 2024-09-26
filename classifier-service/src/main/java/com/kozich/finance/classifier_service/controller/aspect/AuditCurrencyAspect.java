package com.kozich.finance.classifier_service.controller.aspect;

import com.kozich.finance.classifier_service.util.UserHolder;
import com.kozich.finance.classifier_service.controller.feign.client.AuditFeignClient;
import com.kozich.finance.classifier_service.entity.CurrencyEntity;
import com.kozich.finance_storage.core.dto.AuditDTO;
import com.kozich.finance_storage.core.enums.AuditType;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class AuditCurrencyAspect {

    private final AuditFeignClient auditFeignClient;
    private final UserHolder userHolder;

    private final static String TEXT_CREATE = "Создание валюты";

    public AuditCurrencyAspect(AuditFeignClient auditFeignClient, UserHolder userHolder) {
        this.auditFeignClient = auditFeignClient;
        this.userHolder = userHolder;
    }


    @AfterReturning(pointcut = "execution ( * com.kozich.finance.classifier_service.service.impl.CurrencyServiceImpl.create(..))", returning = "currency")
    public void afterCreate(CurrencyEntity currency) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), currency.getUuid().toString(), TEXT_CREATE);
        this.auditFeignClient.create(auditDTO);
    }


    private AuditDTO getAuditDTO(UUID userId, String id, String text) {

        return new AuditDTO()
                .setUserId(userId)
                .setType(AuditType.USER)
                .setId(id)
                .setText(text);

    }

}