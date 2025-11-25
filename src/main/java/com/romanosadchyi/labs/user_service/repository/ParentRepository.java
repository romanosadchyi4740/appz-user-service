package com.romanosadchyi.labs.user_service.repository;

import com.romanosadchyi.labs.user_service.model.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {
    List<Parent> findAll();
}

