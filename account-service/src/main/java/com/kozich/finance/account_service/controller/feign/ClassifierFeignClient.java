package com.kozich.finance.account_service.controller.feign;


import feign.FeignException;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@FeignClient(name = "classifier-service", url = "http://localhost:8083/classifier")
public interface ClassifierFeignClient {

    @GetMapping("/currency/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity getCurrencyById(@PathVariable(value = "uuid") UUID uuid);

    @GetMapping("/operation/category/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity getCategoryById(@PathVariable(value = "uuid") UUID uuid);
}
