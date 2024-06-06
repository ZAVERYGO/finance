package com.kozich.finance.user_service.service.impl;

import com.kozich.finance.user_service.service.MyUserDetails;
import com.kozich.finance.user_service.model.UserEntity;
import com.kozich.finance.user_service.repository.UserRepository;
import com.kozich.finance.user_service.service.api.MyUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsManagerImpl implements MyUserDetailsManager {

    private final UserRepository userRepository;

    public MyUserDetailsManagerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UserEntity userEntity = userRepository.findByEmail(username)
                        .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
                return new MyUserDetails(userEntity);
            }
        };
    }
}
