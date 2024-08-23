package com.kozich.finance.classifier_service.service.api;

import com.kozich.finance.classifier_service.core.dto.CategoryDTO;
import com.kozich.finance.classifier_service.entity.CategoryEntity;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CategoryService {

    CategoryEntity create(CategoryDTO categoryDTO);

    Page<CategoryEntity> getPage(Integer page, Integer size);

    CategoryEntity getById(UUID uuid);

}
