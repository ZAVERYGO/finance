package com.kozich.finance.user_service.service;

import com.kozich.finance.user_service.model.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {

    UserEntity userEntity;
    List<SimpleGrantedAuthority> simpleGrantedAuthorities;

    public MyUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = List.of(new SimpleGrantedAuthority(userEntity.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
