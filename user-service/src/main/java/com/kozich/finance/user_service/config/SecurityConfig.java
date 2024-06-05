package com.kozich.finance.user_service.config;


import com.kozich.finance.user_service.controller.filter.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
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
                .requestMatchers( "/users/public/**").permitAll()
                //Следующие два пример делают одно и тоже
                .requestMatchers("/users/user/test").hasAnyRole("ADMIN") //Обрати внимание что тут нет префикса ROLE_
                .requestMatchers("/users/user/test").hasAnyAuthority("ROLE_ADMIN") //А тут есть
                .requestMatchers(HttpMethod.GET,"/users/user/details").authenticated()
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