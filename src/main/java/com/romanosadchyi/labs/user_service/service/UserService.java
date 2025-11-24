package com.romanosadchyi.labs.user_service.service;

import com.romanosadchyi.labs.user_service.model.dto.StudentDto;
import com.romanosadchyi.labs.user_service.model.dto.UserDto;
import com.romanosadchyi.labs.user_service.model.entity.Student;
import com.romanosadchyi.labs.user_service.model.entity.User;
import com.romanosadchyi.labs.user_service.repository.StudentRepository;
import com.romanosadchyi.labs.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::mapToStudentDto)
                .collect(Collectors.toList());
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return mapToUserDto(user);
    }

    private StudentDto mapToStudentDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setEmail(student.getEmail());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setParentId(student.getParent() != null ? student.getParent().getId() : null);
        return dto;
    }

    private UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setRole(user.getRole());
        return dto;
    }
}

