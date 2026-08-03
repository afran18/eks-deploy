package com.afran.auth_service.service.impl;

import com.afran.auth_service.dto.request.LoginRequest;
import com.afran.auth_service.dto.request.RegisterRequest;
import com.afran.auth_service.dto.response.AuthResponse;
import com.afran.auth_service.entity.User;
import com.afran.auth_service.exception.InvalidCredentialsException;
import com.afran.auth_service.exception.UserAlreadyExistsException;
import com.afran.auth_service.repository.UserRepository;
import com.afran.auth_service.security.JwtService;
import com.afran.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.username())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User savedUser = userRepository.save(user);



        return new AuthResponse(
                savedUser.getUsername(),
                savedUser.getEmail(),
                jwtService.generateToken(savedUser.getUsername()), // JWT Token generated
                LocalDateTime.now()
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid username or password"));

        if(!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        // TODO Generate JWT

        return new AuthResponse(
                user.getUsername(),
                user.getEmail(),
                jwtService.generateToken(user.getUsername()),
                LocalDateTime.now()
        );
    }
}
