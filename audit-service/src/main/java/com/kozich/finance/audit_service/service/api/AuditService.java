package com.kozich.finance.audit_service.service.api;

import com.kozich.finance.audit_service.core.dto.AuditCUDTO;
import com.kozich.finance.audit_service.entity.AuditEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AuditService {

    AuditEntity get(UUID uuid);

    Page<AuditEntity> getPage(Integer page, Integer size);

    void create(AuditCUDTO audit);

}
