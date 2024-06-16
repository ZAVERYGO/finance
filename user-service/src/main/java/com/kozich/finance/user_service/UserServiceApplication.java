package com.kozich.finance.user_service;

import com.kozich.finance.user_service.config.properites.JWTProperty;
import com.kozich.finance.user_service.service.api.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableConfigurationProperties({JWTProperty.class})
@EnableJpaRepositories(basePackages = "com.kozich.finance.user_service.repository")
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableWebMvc
public class UserServiceApplication {

    private UserService userService;
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }


}
