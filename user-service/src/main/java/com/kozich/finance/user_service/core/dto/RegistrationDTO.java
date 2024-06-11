package com.kozich.finance.user_service.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.finance.user_service.core.UserRole;
import com.kozich.finance.user_service.core.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class RegistrationDTO {

    @NotBlank
    @Email
    @JsonProperty("mail")
    private String email;

    @NotBlank
    private String fio;

    @NotBlank
    private String password;

}
