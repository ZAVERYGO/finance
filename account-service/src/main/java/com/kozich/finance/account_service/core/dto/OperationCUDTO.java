package com.kozich.finance.account_service.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Category;
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
public class OperationCUDTO {

    @NotBlank
    private Long date;

    @NotBlank
    private String description;

    @NotBlank
    private Integer value;

    @NotBlank
    private UUID currencyUuid;

    @JsonProperty("category")
    private UUID categoryUuid;

}
