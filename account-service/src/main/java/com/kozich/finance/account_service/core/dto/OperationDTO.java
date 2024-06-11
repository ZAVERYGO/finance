package com.kozich.finance.account_service.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class OperationDTO {

    private UUID uuid;

    private Long date;

    private String description;

    private Integer value;

    private Long dtCreate;

    private Long dtUpdate;

    private UUID currencyUuid;

    private UUID categoryUuid;
}
