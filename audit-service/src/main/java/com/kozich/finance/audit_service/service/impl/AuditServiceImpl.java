package com.kozich.finance.audit_service.service.impl;

import com.kozich.finance.audit_service.core.AuditType;
import com.kozich.finance.audit_service.core.dto.AuditCUDTO;
import com.kozich.finance.audit_service.mapper.AuditMapper;
import com.kozich.finance.audit_service.model.AuditEntity;
import com.kozich.finance.audit_service.repository.AuditRepository;
import com.kozich.finance.audit_service.service.api.AuditService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;
    private final AuditMapper auditMapper;

    public AuditServiceImpl(AuditRepository auditRepository, AuditMapper auditMapper) {
        this.auditRepository = auditRepository;
        this.auditMapper = auditMapper;
    }

    @Override
    public Page<AuditEntity> getPage(Integer page, Integer size) {

        return this.auditRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public void create(AuditCUDTO audit) {

        AuditEntity auditEntity = new AuditEntity()
                .setUuid(UUID.randomUUID())
                .setUser(audit.getUser().getUuid())
                .setText(audit.getText())
                .setDtCreate(LocalDateTime.now())
                .setId(audit.getId())
                .setType(audit.getType());


        this.auditRepository.saveAndFlush(auditEntity);
    }

    @Override
    public AuditEntity get(UUID uuid) {

        Optional<AuditEntity> optional = this.auditRepository.findById(uuid);

        return optional.orElseThrow(() -> new IllegalArgumentException("Нет такого действия"));
    }
}
