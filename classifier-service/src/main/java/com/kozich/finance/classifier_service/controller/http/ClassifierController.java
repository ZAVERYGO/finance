package com.kozich.finance.classifier_service.controller.http;

import com.kozich.finance.classifier_service.core.dto.CategoryDTO;
import com.kozich.finance.classifier_service.core.dto.CurrencyDTO;
import com.kozich.finance.classifier_service.core.dto.PageCurrencyDTO;
import com.kozich.finance.classifier_service.mapper.CurrencyMapper;
import com.kozich.finance.classifier_service.model.CurrencyEntity;
import com.kozich.finance.classifier_service.service.api.CategoryService;
import com.kozich.finance.classifier_service.service.api.CurrencyService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/classifier")
public class ClassifierController {

    private final CurrencyService currencyService;
    private final CategoryService categoryService;
    private final CurrencyMapper currencyMapper;

    public ClassifierController(CurrencyService currencyService, CategoryService categoryService, CurrencyMapper currencyMapper) {
        this.currencyService = currencyService;
        this.categoryService = categoryService;
        this.currencyMapper = currencyMapper;
    }

    @PostMapping("/currency")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CurrencyDTO currencyDTO){

        currencyService.create(currencyDTO);

    }

    @GetMapping("/currency")
    @ResponseStatus(HttpStatus.OK)
    public PageCurrencyDTO getPage(@RequestParam(value = "page") Integer page,
                                   @RequestParam(value = "size") Integer size){

        Page<CurrencyEntity> pageEntity = currencyService.getPage(page, size);

        PageCurrencyDTO pageCurrencyDTO = new PageCurrencyDTO()
                .setNumber(pageEntity.getNumber())
                .setSize(pageEntity.getSize())
                .setTotalPages(pageEntity.getTotalPages())
                .setTotalElements(pageEntity.getTotalElements())
                .setFirst(pageEntity.isFirst())
                .setNumberOfElements(pageEntity.getNumberOfElements())
                .setLast(pageEntity.isLast());

        List<CurrencyEntity> contentEntity = pageEntity.getContent();
        List<CurrencyDTO> contentDTO = new ArrayList<>();

        for (CurrencyEntity userEntity : contentEntity) {
            contentDTO.add(currencyMapper.currencyEntityToCurrencyDTO(userEntity));
        }

        return pageCurrencyDTO.setContent(contentDTO);

    }

    @PostMapping("/operation/category")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody CategoryDTO categoryDTO){

        categoryService.create(categoryDTO);

    }

}
