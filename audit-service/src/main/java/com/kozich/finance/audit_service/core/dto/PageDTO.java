package com.kozich.finance.audit_service.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO {

    private Integer number;
    private Integer size;
    @JsonProperty("total_pages")
    private Integer totalPages;
    @JsonProperty("total_elements")
    private Long totalElements;
    private boolean first;
    @JsonProperty("number_of_elements")
    private Integer numberOfElements;
    private boolean last;
    private List<AuditDTO> content;
}
