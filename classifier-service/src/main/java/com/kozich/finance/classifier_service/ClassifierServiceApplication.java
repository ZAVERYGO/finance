package com.kozich.finance.classifier_service;

import com.kozich.finance.classifier_service.config.properites.JWTProperty;
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
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableJpaRepositories
@EnableWebMvc
public class ClassifierServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassifierServiceApplication.class, args);

    }

}
