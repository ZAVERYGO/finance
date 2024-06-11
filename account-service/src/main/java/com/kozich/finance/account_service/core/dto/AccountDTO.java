package com.kozich.finance.account_service.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.finance.account_service.core.AccountType;
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
public class AccountDTO {

    private UUID uuid;

    private String title;

    private String description;

    private Integer balance;

    private AccountType type;

    @JsonProperty("dt_create")
    private Long dtCreate;

    @JsonProperty("dt_create")
    private Long dtUpdate;

    @JsonProperty("currency")
    private UUID currencyUuid;

}
