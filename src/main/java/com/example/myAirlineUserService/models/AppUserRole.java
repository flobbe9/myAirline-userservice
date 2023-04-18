package com.example.myAirlineUserService.models;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Enum representing the role a user can have granting them certain permissions.
 * 
 * @since 0.0.1
 */
public enum AppUserRole {
    USER,
    ADMIN;


    /**
     * Map all permissions of a role (including the role) as list of {@link SimpleGrantedAuthority}.
     * 
     * @return list of authorities coming along with a role
     */
    public List<SimpleGrantedAuthority> getGrantedAuthorities() {

        return List.of(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}