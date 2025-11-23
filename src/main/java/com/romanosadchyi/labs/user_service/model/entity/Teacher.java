package com.romanosadchyi.labs.user_service.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {
    private String subject;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(Role.TEACHER);
    }
}
