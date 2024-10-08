package com.kozich.finance.classifier_service.service.impl;

import com.kozich.finance.classifier_service.core.dto.CurrencyDTO;
import com.kozich.finance.classifier_service.entity.CurrencyEntity;
import com.kozich.finance.classifier_service.repository.CurrencyRepository;
import com.kozich.finance.classifier_service.service.api.CurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Transactional
    @Override
    public CurrencyEntity create(CurrencyDTO currencyDTO) {

        if (currencyRepository.findByTitle(currencyDTO.getTitle()).isPresent()) {
            throw new IllegalArgumentException("Валюта уже существует");
        }
        LocalDateTime localDateTime = LocalDateTime.now();

        CurrencyEntity currencyEntity = new CurrencyEntity()
                .setUuid(UUID.randomUUID())
                .setTitle(currencyDTO.getTitle())
                .setDescription(currencyDTO.getDescription())
                .setDtCreate(localDateTime)
                .setDtUpdate(localDateTime);

        return currencyRepository.saveAndFlush(currencyEntity);
    }

    @Override
    public Page<CurrencyEntity> getPage(Integer page, Integer size) {
        return currencyRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public CurrencyEntity getById(UUID uuid) {
        return currencyRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("Валюты не существует"));
    }


}
