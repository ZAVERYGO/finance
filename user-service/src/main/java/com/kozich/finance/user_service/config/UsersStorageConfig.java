package com.kozich.finance.user_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class UsersStorageConfig {

    @Bean()
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
