package com.romanosadchyi.labs.user_service.controller;

import com.romanosadchyi.labs.user_service.model.dto.LoginDto;
import com.romanosadchyi.labs.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> getToken(@RequestBody LoginDto authRequest) {
        return ResponseEntity.ok(authService.generateJwtToken(authRequest));
    }
}
