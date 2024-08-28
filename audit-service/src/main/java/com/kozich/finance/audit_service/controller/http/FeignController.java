package com.kozich.finance.audit_service.controller.http;

import com.kozich.finance.audit_service.core.dto.AuditCUDTO;
import com.kozich.finance.audit_service.service.api.AuditService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feign")
public class FeignController {

    private final AuditService auditService;

    public FeignController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody AuditCUDTO audit) {
        this.auditService.create(audit);
    }
}
