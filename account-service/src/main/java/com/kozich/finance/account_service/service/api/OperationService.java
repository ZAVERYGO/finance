package com.kozich.finance.account_service.service.api;

import com.kozich.finance.account_service.core.dto.OperationCUDTO;
import com.kozich.finance.account_service.entity.OperationEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface OperationService {

    OperationEntity getById(UUID uuid);

    Page<OperationEntity> getPage(Integer page, Integer size, UUID accountUuid);

    Page<OperationEntity> getPage(Integer page, Integer size, UUID accountUuid, UUID uuid);

    OperationEntity create(OperationCUDTO operationCUDTO, UUID uuid);

    OperationEntity update(UUID uuid, UUID uuid_operation, OperationCUDTO operationCUDTO, Long dtUpdate);

    void delete(UUID uuid, UUID uuidOperation, Long dtUpdate);

}
