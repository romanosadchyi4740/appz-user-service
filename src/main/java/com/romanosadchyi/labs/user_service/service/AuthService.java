package com.romanosadchyi.labs.user_service.service;

import com.romanosadchyi.labs.user_service.model.dto.LoginDto;
import com.romanosadchyi.labs.user_service.model.entity.User;
import com.romanosadchyi.labs.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public String generateJwtToken(LoginDto authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        if (authenticate.isAuthenticated()) {
            User user = userRepository.findByEmail(authRequest.getEmail()).get();
            return jwtService.generateToken(authRequest.getEmail(), user.getId(), user.getRole().name());
        } else {
            throw new RuntimeException("Invalid Access");
        }
    }
}
