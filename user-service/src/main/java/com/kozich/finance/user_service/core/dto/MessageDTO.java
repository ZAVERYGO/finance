package com.kozich.finance.user_service.core.dto;

import com.kozich.finance.user_service.core.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class MessageDTO {

    private String code;

    private String email;

    private MessageStatus status;

    private LocalDateTime dtCreate;

}
