package com.afran.auth_service.service;

import com.afran.auth_service.dto.request.LoginRequest;
import com.afran.auth_service.dto.request.RegisterRequest;
import com.afran.auth_service.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
