package com.kozich.finance.classifier_service.controller.aspect;

import com.kozich.finance.classifier_service.controller.feign.client.AuditFeignClient;
import com.kozich.finance.classifier_service.entity.CategoryEntity;
import com.kozich.finance.classifier_service.util.UserHolder;
import com.kozich.finance_storage.core.dto.AuditDTO;
import com.kozich.finance_storage.core.enums.AuditType;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class AuditCategoryAspect {

    private final AuditFeignClient auditFeignClient;
    private final UserHolder userHolder;

    private final static String TEXT_CREATE = "Создание категории";

    public AuditCategoryAspect(AuditFeignClient auditFeignClient, UserHolder userHolder) {
        this.auditFeignClient = auditFeignClient;
        this.userHolder = userHolder;
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.classifier_service.service.impl.CategoryServiceImpl.create(..))", returning = "category")
    public void afterCreate(CategoryEntity category) {
        AuditDTO auditDTO = getAuditDTO(userHolder.getUser().getUsername(), category.getUuid().toString(), TEXT_CREATE);
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