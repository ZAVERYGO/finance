package com.kozich.finance.classifier_service.controller.http;

import com.kozich.finance.classifier_service.core.dto.CurrencyDTO;
import com.kozich.finance.classifier_service.service.api.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/classifier")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @PostMapping("/currency")
    @ResponseStatus(HttpStatus.CREATED)
    public void getById(@RequestBody CurrencyDTO currencyDTO){

        currencyService.create(currencyDTO);

    }
}
