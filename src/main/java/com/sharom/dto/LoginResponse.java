package com.sharom.dto;

public record LoginResponse(String accessToken,
                            String refreshToken,
                            long expiresIn) {
}
