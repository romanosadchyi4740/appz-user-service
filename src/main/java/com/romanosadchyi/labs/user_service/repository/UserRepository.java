package com.romanosadchyi.labs.user_service.repository;

import com.romanosadchyi.labs.user_service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
