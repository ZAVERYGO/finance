package com.kozich.finance.classifier_service.repository;

import com.kozich.finance.classifier_service.model.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, UUID> {



}
