package com.kozich.finance.user_service;

import com.kozich.finance.user_service.core.dto.UserCUDTO;
import com.kozich.finance.user_service.service.api.UserService;
import com.kozich.finance_storage.core.enums.UserRole;
import com.kozich.finance_storage.core.enums.UserStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class AdminRunner implements CommandLineRunner {

    private final UserService userService;
    @Value("${admin.fio}")
    private String fio;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.password}")
    private String password;

    public AdminRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {

        if (userService.existsByEmail(email)) {
            return;
        }

        userService.create(new UserCUDTO()
                .setRole(UserRole.ROLE_ADMIN)
                .setFio(fio)
                .setStatus(UserStatus.ACTIVATED)
                .setEmail(email)
                .setPassword(password));
    }

}
