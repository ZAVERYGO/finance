package com.kozich.finance.account_service.service.api;

import com.kozich.finance.account_service.core.dto.AccountCUDTO;
import com.kozich.finance.account_service.core.dto.OperationCUDTO;
import com.kozich.finance.account_service.model.AccountEntity;
import com.kozich.finance.account_service.model.OperationEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    AccountEntity getById(UUID uuid);

    AccountEntity getByMail(String mail);

    AccountEntity getByUuidAndEmail(UUID uuid, String mail);

    Page<AccountEntity> getPage(Integer page, Integer size);

    AccountEntity create(AccountCUDTO accountCUDTO);

    AccountEntity update(UUID uuid, AccountCUDTO accountCUDTO, Long dtUpdate);

    void delete(UUID uuid);

}
