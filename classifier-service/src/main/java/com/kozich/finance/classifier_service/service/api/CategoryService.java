package com.kozich.finance.classifier_service.service.api;

import com.kozich.finance.classifier_service.core.dto.CategoryDTO;
import com.kozich.finance.classifier_service.model.CategoryEntity;

public interface CategoryService {

    CategoryEntity create(CategoryDTO categoryDTO);

}
