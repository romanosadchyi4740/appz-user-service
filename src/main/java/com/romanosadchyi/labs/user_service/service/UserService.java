package com.romanosadchyi.labs.user_service.service;

import com.romanosadchyi.labs.user_service.model.dto.CreateUserRequest;
import com.romanosadchyi.labs.user_service.model.dto.StudentDto;
import com.romanosadchyi.labs.user_service.model.dto.TeacherDto;
import com.romanosadchyi.labs.user_service.model.dto.UserDto;
import com.romanosadchyi.labs.user_service.model.entity.Parent;
import com.romanosadchyi.labs.user_service.model.entity.Role;
import com.romanosadchyi.labs.user_service.model.entity.Student;
import com.romanosadchyi.labs.user_service.model.entity.Teacher;
import com.romanosadchyi.labs.user_service.model.entity.User;
import com.romanosadchyi.labs.user_service.repository.ParentRepository;
import com.romanosadchyi.labs.user_service.repository.StudentRepository;
import com.romanosadchyi.labs.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto createUser(CreateUserRequest request) {
        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User with email " + request.getEmail() + " already exists");
        }

        Role role = Role.valueOf(request.getRole());
        User user;

        switch (role) {
            case STUDENT -> {
                Student student = new Student();
                student.setEmail(request.getEmail());
                student.setPassword(passwordEncoder.encode(request.getPassword()));
                student.setFirstName(request.getFirstName());
                student.setLastName(request.getLastName());
                student.setRole(Role.STUDENT);

                if (request.getParentId() != null) {
                    User parentUser = userRepository.findById(request.getParentId())
                            .orElseThrow(() -> new RuntimeException("Parent not found with id: " + request.getParentId()));
                    if (!(parentUser instanceof Parent parent)) {
                        throw new RuntimeException("User with id " + request.getParentId() + " is not a parent");
                    }
                    student.setParent(parent);
                }

                user = userRepository.save(student);
            }
            case PARENT -> {
                Parent parent = new Parent();
                parent.setEmail(request.getEmail());
                parent.setPassword(passwordEncoder.encode(request.getPassword()));
                parent.setFirstName(request.getFirstName());
                parent.setLastName(request.getLastName());
                parent.setRole(Role.PARENT);
                user = userRepository.save(parent);
            }
            case TEACHER -> {
                if (request.getSubject() == null || request.getSubject().isEmpty()) {
                    throw new RuntimeException("Subject is required for teacher");
                }
                Teacher teacher = new Teacher();
                teacher.setEmail(request.getEmail());
                teacher.setPassword(passwordEncoder.encode(request.getPassword()));
                teacher.setFirstName(request.getFirstName());
                teacher.setLastName(request.getLastName());
                teacher.setRole(Role.TEACHER);
                teacher.setSubject(request.getSubject());
                user = userRepository.save(teacher);
            }
            default -> throw new RuntimeException("Invalid role: " + request.getRole());
        }

        return mapToUserDto(user);
    }

    public List<UserDto> getAllParents() {
        List<Parent> parents = parentRepository.findAll();
        return parents.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
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
        dto.setRole(user.getRole().name());
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
