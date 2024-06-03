package com.kozich.finance.user_service.core.dto;

import com.kozich.finance.user_service.core.UserRole;
import com.kozich.finance.user_service.core.UserStatus;
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
public class UserCUDTO {

    private String email;

    private String fio;

    private UserRole role;

    private UserStatus status;

    private String password;

}
