package com.kozich.finance.classifier_service.controller.http;

import com.kozich.finance.classifier_service.core.dto.CategoryDTO;
import com.kozich.finance.classifier_service.core.dto.CurrencyDTO;
import com.kozich.finance.classifier_service.entity.CategoryEntity;
import com.kozich.finance.classifier_service.entity.CurrencyEntity;
import com.kozich.finance.classifier_service.mapper.CategoryMapper;
import com.kozich.finance.classifier_service.mapper.CurrencyMapper;
import com.kozich.finance.classifier_service.service.api.CategoryService;
import com.kozich.finance.classifier_service.service.api.CurrencyService;
import com.kozich.finance_storage.core.dto.PageDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@Validated
@RequestMapping("/classifier")
public class ClassifierController {

    private final CurrencyService currencyService;
    private final CategoryService categoryService;
    private final CurrencyMapper currencyMapper;
    private final CategoryMapper categoryMapper;

    public ClassifierController(CurrencyService currencyService, CategoryService categoryService, CurrencyMapper currencyMapper, CategoryMapper categoryMapper) {
        this.currencyService = currencyService;
        this.categoryService = categoryService;
        this.currencyMapper = currencyMapper;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping("/currency")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CurrencyDTO currencyDTO) {
        currencyService.create(currencyDTO);
    }

    @GetMapping("/currency")
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<CurrencyDTO> getPageCurrency(@NonNull @PositiveOrZero @RequestParam(value = "page") Integer page,
                                                @NonNull @Positive @RequestParam(value = "size") Integer size) {

        Page<CurrencyEntity> pageEntity = currencyService.getPage(page, size);

        PageDTO<CurrencyDTO> pageCurrencyDTO = new PageDTO<CurrencyDTO>()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<CurrencyEntity> contentEntity = pageEntity.getContent();
        List<CurrencyDTO> contentDTO = new ArrayList<>();

        for (CurrencyEntity currencyEntity : contentEntity) {
            contentDTO.add(currencyMapper.currencyEntityToCurrencyDTO(currencyEntity));
        }

        return pageCurrencyDTO.setContent(contentDTO);

    }

    @PostMapping("/operation/category")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.create(categoryDTO);
    }

    @GetMapping("/operation/category")
    @ResponseStatus(HttpStatus.OK)
    public PageDTO<CategoryDTO> getPageCategory(@NotNull @PositiveOrZero @RequestParam(value = "page") Integer page,
                                           @NotNull @Positive @RequestParam(value = "size") Integer size) {

        Page<CategoryEntity> pageEntity = categoryService.getPage(page, size);

        PageDTO<CategoryDTO> pageCategoryDTO = new PageDTO<CategoryDTO>()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<CategoryEntity> contentEntity = pageEntity.getContent();
        List<CategoryDTO> contentDTO = new ArrayList<>();

        for (CategoryEntity categoryEntity : contentEntity) {
            contentDTO.add(categoryMapper.categoryEntityToCategoryDTO(categoryEntity));
        }

        return pageCategoryDTO.setContent(contentDTO);

    }

}
