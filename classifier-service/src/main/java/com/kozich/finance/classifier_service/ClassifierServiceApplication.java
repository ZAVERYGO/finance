package com.kozich.finance.classifier_service;

import com.kozich.finance.classifier_service.config.properites.JWTProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableConfigurationProperties({JWTProperty.class})
@EnableJpaRepositories
@EnableWebMvc
public class ClassifierServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClassifierServiceApplication.class, args);
    }

}
