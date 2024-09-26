package com.kozich.finance.audit_service.service.impl;

import com.kozich.finance.audit_service.entity.AuditEntity;
import com.kozich.finance.audit_service.repository.AuditRepository;
import com.kozich.finance.audit_service.service.api.AuditService;
import com.kozich.finance_storage.core.dto.AuditDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public Page<AuditEntity> getPage(Integer page, Integer size) {
        return this.auditRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public AuditEntity create(AuditDTO audit) {

        AuditEntity auditEntity = new AuditEntity()
                .setUuid(UUID.randomUUID())
                .setUser(audit.getUserId())
                .setText(audit.getText())
                .setDtCreate(LocalDateTime.now())
                .setId(audit.getId())
                .setType(audit.getType());

        return this.auditRepository.saveAndFlush(auditEntity);
    }

    @Override
    public AuditEntity get(UUID uuid) {
        Optional<AuditEntity> optional = this.auditRepository.findById(uuid);
        return optional.orElseThrow(() -> new IllegalArgumentException("Нет такого действия"));
    }
}
