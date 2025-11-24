package com.romanosadchyi.labs.user_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Long parentId;
}

