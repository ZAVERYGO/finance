package com.kozich.finance.account_service.service.api;

import com.kozich.finance.account_service.core.dto.AccountCUDTO;
import com.kozich.finance.account_service.entity.AccountEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface AccountService {

    AccountEntity getById(UUID uuid);

    AccountEntity getByEmail(String mail);

    AccountEntity getByUuidAndEmail(UUID uuid, String mail);

    Page<AccountEntity> getPage(Integer page, Integer size, String email);

    AccountEntity create(AccountCUDTO accountCUDTO);

    AccountEntity update(UUID uuid, AccountCUDTO accountCUDTO, Long dtUpdate, String email);

}
