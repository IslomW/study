package com.sharom.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.sharom.dto.LoginRequest;
import com.sharom.dto.LoginResponse;
import com.sharom.dto.RefreshRequest;
import com.sharom.dto.RegisterRequest;
import com.sharom.entity.RefreshToken;
import com.sharom.entity.User;
import com.sharom.service.AuthService;
import com.sharom.utils.JwtService;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.*;

@Path("api/v1/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject
    AuthService authService;


    @POST
    @Path("/login")
    public LoginResponse login(LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @POST
    @Path("/register")
    public LoginResponse register(RegisterRequest request) {
        return authService.register(request);
    }


    @POST
    @Path("/refresh")
    public LoginResponse refresh(RefreshRequest request) {
        return authService.refresh(request);
    }

    @POST
    @Path("/logout-all")
    @Authenticated
    public void logoutAll(@Context SecurityContext ctx) {
        authService.logoutAll(ctx.getUserPrincipal().getName());
    }


}
