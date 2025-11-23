package com.romanosadchyi.labs.user_service.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    STUDENT,
    TEACHER,
    PARENT;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
