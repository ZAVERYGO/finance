package com.kozich.finance.user_service.controller.feign.client;

import com.kozich.finance_storage.core.dto.AuditDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient(name = "audit-service", url = "${url.audit}")
public interface AuditFeignClient {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody() AuditDTO audit);

}
