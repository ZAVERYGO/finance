package com.kozich.finance;

import com.kozich.finance.audit_service.config.properites.JWTProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableConfigurationProperties({JWTProperty.class})
@EnableFeignClients
@EnableJpaRepositories(basePackages = "com.kozich.finance.audit_service.repository")
@EnableWebMvc
public class AuditServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuditServiceApplication.class,args);
    }
}
