package com.kozich.finance.user_service.service.api;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface MyUserDetailsManager {

    UserDetailsService userDetailsService();

}
