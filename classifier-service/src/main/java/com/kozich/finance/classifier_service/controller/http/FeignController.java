package com.kozich.finance.classifier_service.controller.http;

import com.kozich.finance.classifier_service.core.dto.CategoryDTO;
import com.kozich.finance.classifier_service.core.dto.CurrencyDTO;
import com.kozich.finance.classifier_service.mapper.CategoryMapper;
import com.kozich.finance.classifier_service.mapper.CurrencyMapper;
import com.kozich.finance.classifier_service.service.api.CategoryService;
import com.kozich.finance.classifier_service.service.api.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Validated
@RequestMapping("/feign")
public class FeignController {

    private final CurrencyService currencyService;
    private final CategoryService categoryService;
    private final CurrencyMapper currencyMapper;
    private final CategoryMapper categoryMapper;

    public FeignController(CurrencyService currencyService, CategoryService categoryService, CurrencyMapper currencyMapper, CategoryMapper categoryMapper) {
        this.currencyService = currencyService;
        this.categoryService = categoryService;
        this.currencyMapper = currencyMapper;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/currency/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public CurrencyDTO getCurrencyById(@PathVariable(value = "uuid") UUID uuid) {
        return currencyMapper.currencyEntityToCurrencyDTO(currencyService.getById(uuid));
    }

    @GetMapping("/operation/category/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDTO getCategoryById(@PathVariable(value = "uuid") UUID uuid) {
        return categoryMapper.categoryEntityToCategoryDTO(categoryService.getById(uuid));
    }
}
