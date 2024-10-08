package com.kozich.finance.account_service.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.finance.account_service.core.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
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
public class AccountCUDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    private AccountType type;

    @JsonProperty("currency")
    private UUID currencyUuid;

}
