package com.kozich.finance.classifier_service.service.api;

import com.kozich.finance.classifier_service.core.dto.CurrencyDTO;
import com.kozich.finance.classifier_service.entity.CurrencyEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CurrencyService {

    CurrencyEntity create(CurrencyDTO currencyDTO);

    Page<CurrencyEntity> getPage(Integer page, Integer size);

    CurrencyEntity getById(UUID uuid);

}
