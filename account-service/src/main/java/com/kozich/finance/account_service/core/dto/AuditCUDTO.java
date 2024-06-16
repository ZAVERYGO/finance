package com.kozich.finance.account_service.core.dto;

import com.kozich.finance.account_service.core.AuditType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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
