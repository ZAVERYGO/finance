package com.kozich.finance.user_service.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kozich.finance.user_service.core.UserRole;
import com.kozich.finance.user_service.core.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.Accessors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class UserCUDTO {

    @NotBlank
    @Email
    @JsonProperty("mail")
    private String email;

    @NotBlank
    private String fio;

    private UserRole role;

    private UserStatus status;

    @NotBlank
    private String password;


}
