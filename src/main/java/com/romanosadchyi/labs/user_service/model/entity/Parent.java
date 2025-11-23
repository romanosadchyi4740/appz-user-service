package com.romanosadchyi.labs.user_service.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parent extends User {
    @OneToMany(mappedBy = "parent")
    private List<Student> children;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(Role.PARENT);
    }
}
