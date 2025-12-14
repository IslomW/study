package com.sharom.service;

import com.sharom.dto.LoginRequest;
import com.sharom.dto.LoginResponse;
import com.sharom.dto.RefreshRequest;
import com.sharom.dto.RegisterRequest;
import com.sharom.entity.User;

public interface AuthService {

    LoginResponse login(LoginRequest loginRequest);

    LoginResponse register(RegisterRequest request);

    LoginResponse refresh(RefreshRequest request);

    void logoutAll(String email);

    void resetPassword(String email);


}
