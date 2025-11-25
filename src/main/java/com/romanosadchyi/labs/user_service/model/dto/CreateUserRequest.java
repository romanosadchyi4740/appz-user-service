package com.romanosadchyi.labs.user_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role; // STUDENT, PARENT, or TEACHER
    private Long parentId; // Required if role is STUDENT
    private String subject; // Required if role is TEACHER
}

