package com.romanosadchyi.labs.user_service.service;

import com.romanosadchyi.labs.user_service.model.dto.StudentDto;
import com.romanosadchyi.labs.user_service.model.dto.TeacherDto;
import com.romanosadchyi.labs.user_service.model.dto.UserDto;
import com.romanosadchyi.labs.user_service.model.entity.Parent;
import com.romanosadchyi.labs.user_service.model.entity.Student;
import com.romanosadchyi.labs.user_service.model.entity.Teacher;
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

    public List<StudentDto> getChildrenByParentId(Long parentId) {
        User user = userRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found with id: " + parentId));
        
        if (!(user instanceof Parent parent)) {
            throw new RuntimeException("User with id " + parentId + " is not a parent");
        }
        
        return parent.getChildren().stream()
                .map(this::mapToStudentDto)
                .collect(Collectors.toList());
    }

    public TeacherDto getTeacherById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        if (!(user instanceof Teacher teacher)) {
            throw new RuntimeException("User with id " + id + " is not a teacher");
        }
        
        return mapToTeacherDto(teacher);
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

    private TeacherDto mapToTeacherDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setEmail(teacher.getEmail());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setRole(teacher.getRole().name());
        dto.setSubject(teacher.getSubject());
        return dto;
    }
}
