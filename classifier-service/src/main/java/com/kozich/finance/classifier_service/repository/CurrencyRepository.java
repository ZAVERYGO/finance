package com.kozich.finance.classifier_service.repository;

import com.kozich.finance.classifier_service.model.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, UUID> {

    Optional<CurrencyEntity> findByTitle(String title);


}
