package com.kozich.finance.user_service.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.finance.user_service.core.AuditType;
import com.kozich.finance.user_service.core.UserRole;
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
public class AuditCUDTO {

    private UserAuditDTO user;

    private String text;

    private AuditType type;

    private String id;
}
