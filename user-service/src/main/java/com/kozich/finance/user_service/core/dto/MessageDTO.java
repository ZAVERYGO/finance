package com.kozich.finance.user_service.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.finance.user_service.core.MessageStatus;
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
public class MessageDTO {

    private String code;

    @JsonProperty("mail")
    private String email;

    private MessageStatus status;

    @JsonProperty("dt_create")
    private Long dtCreate;

}
