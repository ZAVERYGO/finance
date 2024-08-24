package com.kozich.finance.account_service.service.impl;

import com.kozich.finance.account_service.config.user_info.UserHolder;
import com.kozich.finance.account_service.controller.feign.client.ClassifierFeignClient;
import com.kozich.finance.account_service.core.dto.AccountCUDTO;
import com.kozich.finance.account_service.entity.AccountEntity;
import com.kozich.finance.account_service.repository.AccountRepository;
import com.kozich.finance.account_service.service.api.AccountService;
import feign.FeignException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClassifierFeignClient classifierFeignClient;
    private final UserHolder userHolder;


    public AccountServiceImpl(AccountRepository accountRepository, ClassifierFeignClient classifierFeignClient,
                              UserHolder userHolder) {
        this.accountRepository = accountRepository;
        this.classifierFeignClient = classifierFeignClient;
        this.userHolder = userHolder;
    }

    @Override
    public AccountEntity getById(UUID uuid) {
        return accountRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("Не существует счета"));
    }

    @Override
    public AccountEntity getByMail(String mail) {
        return accountRepository.findByEmail(mail).orElseThrow(() -> new IllegalArgumentException("Нет такого счета"));
    }

    @Override
    public AccountEntity getByUuidAndEmail(UUID uuid, String mail) {
        return accountRepository.findByUuidAndEmail(uuid, mail).orElseThrow(() -> new IllegalArgumentException("У вас нет такого счета"));
    }

    @Override
    public Page<AccountEntity> getPage(Integer page, Integer size) {
        return accountRepository.findAllByEmail(PageRequest.of(page, size), userHolder.getUser().getUsername());
    }

    @Transactional
    @Override
    public AccountEntity create(AccountCUDTO accountCUDTO) {

        LocalDateTime localDateTime = LocalDateTime.now();

        try {
            classifierFeignClient.getCurrencyById(accountCUDTO.getCurrencyUuid());
        } catch (FeignException e) {
            throw new IllegalArgumentException("Указанной валюты не существует");
        }

        AccountEntity accountEntity = new AccountEntity()
                .setUuid(UUID.randomUUID())
                .setTitle(accountCUDTO.getTitle())
                .setDescription(accountCUDTO.getDescription())
                .setBalance(0)
                .setType(accountCUDTO.getType())
                .setDtCreate(localDateTime)
                .setDtUpdate(localDateTime)
                .setCurrencyUuid(accountCUDTO.getCurrencyUuid())
                .setEmail(userHolder.getUser().getUsername());

        return accountRepository.saveAndFlush(accountEntity);
    }

    @Transactional
    @Override
    public AccountEntity update(UUID uuid, AccountCUDTO accountCUDTO, Long dtUpdate) {

        Optional<AccountEntity> accountEntity = accountRepository.findById(uuid);

        try {
            classifierFeignClient.getCurrencyById(accountCUDTO.getCurrencyUuid());
        } catch (FeignException e) {
            throw new IllegalArgumentException("Указанной валюты не существует");
        }


        if (accountEntity.isEmpty()) {
            throw new IllegalArgumentException("Счета не существует");
        }
        Long dateTime = accountEntity.get().getDtUpdate().atZone(ZoneId.systemDefault()).toEpochSecond();
        if (!dateTime.equals(dtUpdate)) {
            throw new IllegalArgumentException("Счет уже был изменен");
        }

        AccountEntity accountEntityRes = accountEntity.get()
                .setTitle(accountCUDTO.getTitle())
                .setDescription(accountCUDTO.getDescription())
                .setType(accountCUDTO.getType())
                .setCurrencyUuid(accountCUDTO.getCurrencyUuid());

        return accountRepository.saveAndFlush(accountEntityRes);
    }

}
