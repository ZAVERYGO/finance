package com.kozich.finance.classifier_service.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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
public class CategoryDTO {

    private UUID uuid;

    @NotBlank
    private String title;

    @JsonProperty("dt_create")
    private Long dtCreate;

    @JsonProperty("dt_update")
    private Long dtUpdate;

}
