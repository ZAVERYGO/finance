package com.kozich.finance.account_service.repository;

import com.kozich.finance.account_service.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    Optional<AccountEntity> findByEmail(String mail);

    Page<AccountEntity> findAllByEmail(PageRequest pageRequest, String mail);

    Optional<AccountEntity> findByUuidAndEmail(UUID uuid, String mail);

}
