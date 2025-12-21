package com.sharom.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.sharom.dto.LoginRequest;
import com.sharom.dto.LoginResponse;
import com.sharom.dto.RefreshRequest;
import com.sharom.dto.RegisterRequest;
import com.sharom.entity.RefreshToken;
import com.sharom.entity.Role;
import com.sharom.entity.User;
import com.sharom.exception.BadRequestException;
import com.sharom.repository.UserRepository;
import com.sharom.service.AuthService;
import com.sharom.service.UserService;
import com.sharom.utils.JwtService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    UserService userService;

    @Inject
    JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow(BadRequestException::userNotFound);

        BCrypt.Result result = BCrypt.verifyer()
                .verify(loginRequest.password().toCharArray(), user.getPasswordHash());

        if (!result.verified) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateJwtToken(user);
        String refreshToken = jwtService.createRefreshToken(user).getToken();


        return new LoginResponse(
                token,
                refreshToken,
                jwtService.getAccessTokenTtl()

        );
    }

    @Override
    public LoginResponse register(RegisterRequest request) {

        User user = new User();
        user.setEmail(request.email());
        user.setDisplayName(request.displayName());
        user.setPasswordHash(request.password());
        user.setRole(Role.USER);

        User saveUser = userService.createUser(user);

        String token = jwtService.generateJwtToken(saveUser);
        RefreshToken refreshToken = jwtService.createRefreshToken(saveUser);

        return new LoginResponse(
                token,
                refreshToken.getToken(),
                jwtService.getAccessTokenTtl()

        );
    }


    @Override
    public LoginResponse refresh(RefreshRequest request) {

        RefreshToken oldToken = jwtService.validate(request.refreshToken());

        jwtService.revoke(oldToken);

        String newToken = jwtService.generateJwtToken(oldToken.getUser());

        RefreshToken newRefreshToken = jwtService.createRefreshToken(oldToken.getUser());

        return new LoginResponse(
                newToken,
                newRefreshToken.getToken(),
                jwtService.getAccessTokenTtl());
    }

    @Override
    public void logoutAll(String email) {
        User user = userService.getUserByEmail(email);
        jwtService.revokeAll(user);
    }


    @Override
    public void resetPassword(String email) {

    }
}
