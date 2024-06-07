package com.kozich.finance.classifier_service.core.dto;

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

    private String title;

    private LocalDateTime dtCreate;

    private LocalDateTime dtUpdate;

}
