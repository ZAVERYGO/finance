package com.kozich.finance_storage.core.dto;


import com.kozich.finance_storage.core.enums.AuditType;
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
public class AuditDTO {

    private UUID userId;

    private String text;

    private AuditType type;

    private String id;

}
