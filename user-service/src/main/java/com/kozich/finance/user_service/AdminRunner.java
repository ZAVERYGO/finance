package com.kozich.finance.user_service;

import com.kozich.finance.user_service.core.UserRole;
import com.kozich.finance.user_service.core.UserStatus;
import com.kozich.finance.user_service.core.dto.UserCUDTO;
import com.kozich.finance.user_service.service.api.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class AdminRunner implements CommandLineRunner {

    private final UserService userService;

    public AdminRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userService.existsByEmail("ZAVERYGO@mail.ru")){
            return;
        }
        userService.create(new UserCUDTO()
                .setRole(UserRole.ROLE_ADMIN)
                .setFio("Козич Н. Ю.")
                .setStatus(UserStatus.ACTIVATED)
                .setEmail("ZAVERYGO@mail.ru")
                .setPassword("123"));

    }
}
