package com.kozich.finance.audit_service.util;

import com.kozich.finance_storage.core.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class CustomUserDetails {

    private final UserDTO userDTO;
    private final List<SimpleGrantedAuthority> simpleGrantedAuthorities;

    public CustomUserDetails(UserDTO userDTO) {
        this.userDTO = userDTO;
        simpleGrantedAuthorities = List.of(new SimpleGrantedAuthority(userDTO.getRole().name()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return simpleGrantedAuthorities;
    }

    public String getPassword() {
        return userDTO.getPassword();
    }

    public UUID getUsername() {
        return userDTO.getUuid();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }
}
