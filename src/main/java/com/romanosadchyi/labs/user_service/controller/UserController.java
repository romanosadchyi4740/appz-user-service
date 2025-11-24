package com.romanosadchyi.labs.user_service.controller;

import com.romanosadchyi.labs.user_service.model.dto.StudentDto;
import com.romanosadchyi.labs.user_service.model.dto.TeacherDto;
import com.romanosadchyi.labs.user_service.model.dto.UserDto;
import com.romanosadchyi.labs.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.ok(userService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/parents/{parentId}/children")
    public ResponseEntity<List<StudentDto>> getChildrenByParentId(@PathVariable Long parentId) {
        return ResponseEntity.ok(userService.getChildrenByParentId(parentId));
    }

    @GetMapping("/teachers/{id}")
    public ResponseEntity<TeacherDto> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getTeacherById(id));
    }
}

