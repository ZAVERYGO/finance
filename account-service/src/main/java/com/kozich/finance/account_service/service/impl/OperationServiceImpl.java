package com.kozich.finance.account_service.service.impl;

import com.kozich.finance.account_service.controller.feign.ClassifierFeignClient;
import com.kozich.finance.account_service.core.dto.*;
import com.kozich.finance.account_service.model.OperationEntity;
import com.kozich.finance.account_service.model.AccountEntity;
import com.kozich.finance.account_service.repository.OperationRepository;
import com.kozich.finance.account_service.service.UserHolder;
import com.kozich.finance.account_service.service.api.AccountService;
import com.kozich.finance.account_service.service.api.OperationService;
import feign.FeignException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;
    private final ClassifierFeignClient classifierFeignClient;
    private final UserHolder userHolder;
    private final AccountService accountService;

    public OperationServiceImpl(OperationRepository operationRepository, ClassifierFeignClient classifierFeignClient, UserHolder userHolder, AccountService accountService) {
        this.operationRepository = operationRepository;
        this.classifierFeignClient = classifierFeignClient;
        this.userHolder = userHolder;
        this.accountService = accountService;
    }

    @Override
    public OperationEntity getById(UUID uuid) {
        return null;
    }

    @Override
    public Page<OperationEntity> getPage(Integer page, Integer size, UUID accountUuid) {
        AccountEntity byId = accountService.getById(accountUuid);
        return operationRepository.findAllByAccountEntity(PageRequest.of(page, size), byId);
    }

    @Override
    public OperationEntity create(OperationCUDTO operationCUDTO, UUID uuid) {

        LocalDateTime localDateTime = LocalDateTime.now();

        try {
            classifierFeignClient.getCategoryById(operationCUDTO.getCategoryUuid());
            classifierFeignClient.getCurrencyById(operationCUDTO.getCurrencyUuid());
        }catch (FeignException e){
            throw  new IllegalArgumentException("Не сущесвует указанной валюты или счета");
        }

        AccountEntity byMail = accountService.getByMail(userHolder.getUser().getUsername());

        LocalDateTime date = Instant.ofEpochMilli(operationCUDTO.getDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        OperationEntity operationEntity = new OperationEntity()
                .setUuid(UUID.randomUUID())
                .setDescription(operationCUDTO.getDescription())
                .setDate(date)
                .setValue(operationCUDTO.getValue())
                .setDtCreate(localDateTime)
                .setDtUpdate(localDateTime)
                .setCurrencyUuid(operationCUDTO.getCurrencyUuid())
                .setCatagoryUuid(operationCUDTO.getCategoryUuid())
                .setAccountEntity(byMail);

        return operationRepository.saveAndFlush(operationEntity);
    }

    @Override
    public OperationEntity update(UUID uuid, UUID uuidOperation, OperationCUDTO operationCUDTO, Long dtUpdate) {

        AccountEntity accountEntity = accountService.getById(uuid);
        Optional<OperationEntity> operationEntity = operationRepository.findByUuidAndAccountEntity(uuidOperation, accountEntity);

        try {
            classifierFeignClient.getCategoryById(operationCUDTO.getCategoryUuid());
            classifierFeignClient.getCurrencyById(operationCUDTO.getCurrencyUuid());
        }catch (FeignException e){
            throw  new IllegalArgumentException("Не сущесвует указанной валюты или счета");
        }

        if(operationEntity.isEmpty()){
            throw new IllegalArgumentException("Операции не существует");
        }
        Long dateTime = operationEntity.get().getDtUpdate().toInstant(ZoneOffset.UTC).toEpochMilli();
        if(!dateTime.equals(dtUpdate)){
            throw new IllegalArgumentException("Операция уже была изменена");
        }

        LocalDateTime date = Instant.ofEpochMilli(operationCUDTO.getDate()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        OperationEntity operationEntityRes = operationEntity.get()
                .setDate(date)
                .setDescription(operationCUDTO.getDescription())
                .setCatagoryUuid(operationCUDTO.getCategoryUuid())
                .setValue(operationCUDTO.getValue())
                .setCurrencyUuid(operationCUDTO.getCurrencyUuid());

        return operationRepository.saveAndFlush(operationEntityRes);
    }

    @Override
    public void delete(UUID uuid, UUID uuid_operation, Long dtUpdate) {
        AccountEntity byId = accountService.getById(uuid);
        Optional<OperationEntity> byIdAndAccountUuid = operationRepository.findByUuidAndAccountEntity(uuid, byId);
        if(byIdAndAccountUuid.isEmpty()){
            throw new IllegalArgumentException("Не существует такой операции");
        }
        operationRepository.deleteById(byIdAndAccountUuid.get().getUuid());
    }

}
