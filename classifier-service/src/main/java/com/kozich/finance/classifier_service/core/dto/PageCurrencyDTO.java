package com.kozich.finance.classifier_service.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class PageCurrencyDTO {

    private Integer number;

    private Integer size;

    private Integer totalPages;

    private Long totalElements;

    private Boolean first;

    private Integer numberOfElements;

    private Boolean last;

    private List<CurrencyDTO> content;

}

