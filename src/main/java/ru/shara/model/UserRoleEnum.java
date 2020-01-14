package ru.shara.model;

import org.springframework.security.core.GrantedAuthority;


public enum UserRoleEnum implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
