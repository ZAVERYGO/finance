package com.kozich.finance.classifier_service.feign.aspect;

import com.kozich.finance.classifier_service.core.AuditType;
import com.kozich.finance.classifier_service.core.dto.AuditCUDTO;
import com.kozich.finance.classifier_service.core.dto.UserAuditDTO;
import com.kozich.finance.classifier_service.core.dto.UserDTO;
import com.kozich.finance.classifier_service.feign.client.AuditFeignClient;
import com.kozich.finance.classifier_service.feign.client.UserFeignClient;
import com.kozich.finance.classifier_service.model.CurrencyEntity;
import com.kozich.finance.classifier_service.service.UserHolder;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuditCurrencyAspect {

    private final AuditFeignClient auditFeignClient;
    private final UserFeignClient userFeignClient;
    private final UserHolder userHolder;
    private final static String TEXT_CREATE = "Создание валюты";
    private final static String TEXT_GET_ALL = "Получение списка валют";

    public AuditCurrencyAspect(AuditFeignClient auditFeignClient, UserFeignClient userFeignClient, UserHolder userHolder) {
        this.auditFeignClient = auditFeignClient;
        this.userFeignClient = userFeignClient;
        this.userHolder = userHolder;
    }


    @AfterReturning(pointcut = "execution ( * com.kozich.finance.classifier_service.service.impl.CurrencyServiceImpl.create(..))", returning = "currency")
    public void afterCreate(CurrencyEntity currency) {

        UserAuditDTO userAuditDTO = getUserAudit();

        AuditCUDTO audit = getAuditCUDTO(TEXT_CREATE, userAuditDTO, currency.getUuid().toString());

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
                .setType(AuditType.CURRENCY)
                .setId(id)
                .setText(text);

        return auditCUDTO;
    }
}