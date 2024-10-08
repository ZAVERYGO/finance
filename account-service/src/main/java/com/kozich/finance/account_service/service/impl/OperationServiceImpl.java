package com.kozich.finance.account_service.service.impl;

import com.kozich.finance.account_service.util.UserHolder;
import com.kozich.finance.account_service.controller.feign.client.ClassifierFeignClient;
import com.kozich.finance.account_service.core.dto.OperationCUDTO;
import com.kozich.finance.account_service.entity.AccountEntity;
import com.kozich.finance.account_service.entity.OperationEntity;
import com.kozich.finance.account_service.repository.OperationRepository;
import com.kozich.finance.account_service.service.api.AccountService;
import com.kozich.finance.account_service.service.api.OperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final ClassifierFeignClient classifierFeignClient;
    private final UserHolder userHolder;
    private final AccountService accountService;

    public OperationServiceImpl(OperationRepository operationRepository, ClassifierFeignClient classifierFeignClient,
                                UserHolder userHolder, AccountService accountService) {
        this.operationRepository = operationRepository;
        this.classifierFeignClient = classifierFeignClient;
        this.userHolder = userHolder;
        this.accountService = accountService;
    }

    @Override
    public OperationEntity getById(UUID uuid) {
        return operationRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("Нет такой операции"));
    }

    @Override
    public Page<OperationEntity> getPage(Integer page, Integer size, UUID accountUuid) {
        AccountEntity byId = accountService.getById(accountUuid);
        return operationRepository.findAllByAccountEntity(PageRequest.of(page, size), byId);
    }

    @Override
    public Page<OperationEntity> getPage(Integer page, Integer size, UUID accountUuid, UUID userId) {
        AccountEntity byId = accountService.getByUuidAndUserId(accountUuid, userId);
        return operationRepository.findAllByAccountEntity(PageRequest.of(page, size), byId);
    }


    @Transactional
    @Override
    public OperationEntity create(OperationCUDTO operationCUDTO, UUID uuid) {

        LocalDateTime localDateTime = LocalDateTime.now();

        classifierFeignClient.getCategoryById(operationCUDTO.getCategoryUuid());
        classifierFeignClient.getCurrencyById(operationCUDTO.getCurrencyUuid());

        AccountEntity byMail = accountService.getByUuidAndUserId(uuid, userHolder.getUser().getUsername());

        LocalDateTime date = Instant.ofEpochMilli(operationCUDTO.getDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        OperationEntity operationEntity = new OperationEntity()
                .setUuid(UUID.randomUUID())
                .setDescription(operationCUDTO.getDescription())
                .setDate(date)
                .setValue(operationCUDTO.getValue())
                .setDtCreate(localDateTime)
                .setDtUpdate(localDateTime)
                .setCurrencyUuid(operationCUDTO.getCurrencyUuid())
                .setCategoryUuid(operationCUDTO.getCategoryUuid())
                .setAccountEntity(byMail);

        return operationRepository.saveAndFlush(operationEntity);
    }

    @Transactional
    @Override
    public OperationEntity update(UUID uuid, UUID uuidOperation, OperationCUDTO operationCUDTO, Long dtUpdate) {

        AccountEntity accountEntity = accountService.getById(uuid);
        Optional<OperationEntity> operationEntity = operationRepository.findByUuidAndAccountEntity(uuidOperation, accountEntity);

        classifierFeignClient.getCategoryById(operationCUDTO.getCategoryUuid());
        classifierFeignClient.getCurrencyById(operationCUDTO.getCurrencyUuid());

        if (operationEntity.isEmpty()) {
            throw new IllegalArgumentException("Операции не существует");
        }
        Long dateTime = operationEntity.get().getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();
        if (!dateTime.equals(dtUpdate)) {
            throw new IllegalArgumentException("Операция уже была изменена");
        }

        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochSecond(operationCUDTO.getDate()), ZoneId.systemDefault());
        OperationEntity operationEntityRes = operationEntity.get()
                .setDate(date)
                .setDescription(operationCUDTO.getDescription())
                .setCategoryUuid(operationCUDTO.getCategoryUuid())
                .setValue(operationCUDTO.getValue())
                .setCurrencyUuid(operationCUDTO.getCurrencyUuid());

        return operationRepository.saveAndFlush(operationEntityRes);
    }

    @Override
    public void delete(UUID uuid, UUID uuidOperation, Long dtUpdate) {

        AccountEntity byId = accountService.getById(uuid);
        Optional<OperationEntity> byIdAndAccountUuid = operationRepository.findByUuidAndAccountEntity(uuidOperation, byId);
        if (byIdAndAccountUuid.isEmpty()) {
            throw new IllegalArgumentException("Не существует такой операции");
        }

        Long dateTime = byIdAndAccountUuid.get().getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();
        if (!dateTime.equals(dtUpdate)) {
            throw new IllegalArgumentException("Операция уже была изменена");
        }
        operationRepository.deleteById(uuidOperation);
    }

}
