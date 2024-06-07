package com.kozich.finance.classifier_service.service.impl;

import com.kozich.finance.classifier_service.core.dto.CategoryDTO;
import com.kozich.finance.classifier_service.model.CategoryEntity;
import com.kozich.finance.classifier_service.repository.CategoryRepository;
import com.kozich.finance.classifier_service.service.api.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryEntity create(CategoryDTO categoryDTO) {

        LocalDateTime localDateTime = LocalDateTime.now();

        CategoryEntity categoryEntity = new CategoryEntity()
                .setUuid(UUID.randomUUID())
                .setTitle(categoryDTO.getTitle())
                .setDtCreate(localDateTime)
                .setDtUpdate(localDateTime);

        return categoryRepository.saveAndFlush(categoryEntity);

    }
}
