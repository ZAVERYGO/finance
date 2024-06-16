package com.kozich.finance.classifier_service.config;


import com.kozich.finance.classifier_service.controller.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception  {
        // Enable CORS and disable CSRF
        http = http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable);

        // Set session management to stateless
        http = http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Set unauthorized requests exception handler
        http = http
                .exceptionHandling(eh -> eh.authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.setStatus(
                                    HttpServletResponse.SC_UNAUTHORIZED
                            );
                        }
                ).accessDeniedHandler((request, response, ex) -> {
                    response.setStatus(
                            HttpServletResponse.SC_FORBIDDEN
                    );
                }));

        // Set permissions on endpoints
        http.authorizeHttpRequests(requests -> requests
                // Our public endpoints
                .requestMatchers(HttpMethod.GET,"/classifier/currency").permitAll()
                .requestMatchers(HttpMethod.POST,"/classifier/currency").hasAnyRole("ADMIN")//Обрати внимание что тут нет префикса ROLE_
                .requestMatchers(HttpMethod.POST, "/classifier/operation/category").hasAnyRole("ADMIN")  //А тут есть
                .requestMatchers(HttpMethod.GET, "/classifier/operation/category").permitAll()
                .requestMatchers(HttpMethod.GET, "/classifier/currency/{uuid}").permitAll()
                .requestMatchers(HttpMethod.GET, "/classifier/operation/category/{uuid}").permitAll()
                .requestMatchers(HttpMethod.GET, "/feign/**").permitAll()
                // Our private endpoints
                .anyRequest().authenticated()
        );

        // Add JWT token filter
        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}