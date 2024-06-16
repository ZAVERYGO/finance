package com.kozich.finance.user_service.feign.client;

import com.kozich.finance.user_service.core.dto.AuditCUDTO;
import feign.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "audit-service", url = "${url.audit}")
public interface AuditFeignClient {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody() AuditCUDTO audit);

}
