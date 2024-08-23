package com.kozich.finance.account_service.repository;

import com.kozich.finance.account_service.entity.AccountEntity;
import com.kozich.finance.account_service.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, UUID> {

    Page<OperationEntity>  findAllByAccountEntity(PageRequest pageRequest, AccountEntity accountEntity);

    Optional<OperationEntity> findByUuidAndAccountEntity(UUID uuidOperation, AccountEntity accountEntity);

}
