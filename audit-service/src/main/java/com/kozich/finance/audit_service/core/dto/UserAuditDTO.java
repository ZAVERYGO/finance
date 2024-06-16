package com.kozich.finance.audit_service.core.dto;

import com.kozich.finance.audit_service.core.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserAuditDTO {
    private UUID uuid;
    private String mail;
    private String fio;
    private UserRole role;
}
