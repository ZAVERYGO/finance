package com.kozich.finance.classifier_service.controller.feign.aspect;

import com.kozich.finance.classifier_service.util.UserHolder;
import com.kozich.finance.classifier_service.controller.feign.client.AuditFeignClient;
import com.kozich.finance.classifier_service.controller.feign.client.UserFeignClient;
import com.kozich.finance.classifier_service.core.dto.AuditCUDTO;
import com.kozich.finance.classifier_service.core.dto.UserAuditDTO;
import com.kozich.finance.classifier_service.core.dto.UserDTO;
import com.kozich.finance.classifier_service.core.enums.AuditType;
import com.kozich.finance.classifier_service.entity.CategoryEntity;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditCategoryAspect {

    private final AuditFeignClient auditFeign;
    private final UserFeignClient userFeignClient;
    private final UserHolder userHolder;

    private final static String TEXT_CREATE = "Создание категории";

    public AuditCategoryAspect(AuditFeignClient auditFeign, UserFeignClient userFeignClient, UserHolder userHolder) {
        this.auditFeign = auditFeign;
        this.userFeignClient = userFeignClient;
        this.userHolder = userHolder;
    }

    @AfterReturning(pointcut = "execution( * com.kozich.finance.classifier_service.service.impl.CategoryServiceImpl.create(..))", returning = "category")
    public void afterCreate(CategoryEntity category) {
        UserAuditDTO userAuditDTO = getUserAudit();
        AuditCUDTO audit = getAuditCUDTO(TEXT_CREATE, userAuditDTO, category.getUuid().toString());
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
                .setType(AuditType.CATEGORY)
                .setId(id)
                .setText(text);

        return auditCUDTO;
    }
}