package com.sharom.utils;

import com.sharom.entity.RefreshToken;
import com.sharom.entity.User;
import com.sharom.repository.RefreshTokenRepository;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class JwtService {

    @Inject
    RefreshTokenRepository repository;


    private static final long ACCESS_TTL = 3600;
    private static final long REFRESH_TTL_DAYS = 30;

    public String generateJwtToken(User user) {

        return Jwt.issuer("study-service")
                .subject(user.getEmail())
                .groups(Set.of(user.getRole().name()))
                .claim("lang", user.getLang())
                .expiresAt(Instant.now().plusSeconds(ACCESS_TTL))
                .sign();
    }

    public long getAccessTokenTtl() {
        return ACCESS_TTL;
    }

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        RefreshToken rt = new RefreshToken();
        rt.setUser(user);
        rt.setToken(UUID.randomUUID().toString());
        rt.setExpiryDate(Instant.now().plus(REFRESH_TTL_DAYS, ChronoUnit.DAYS));
        repository.persist(rt);
        return rt;
    }

    public RefreshToken validate(String token) {
        RefreshToken rt = repository.findValidToken(token);

        if (rt == null || rt.getExpiryDate().isBefore(Instant.now())) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        return rt;
    }

    public void revoke(RefreshToken token) {
        token.setRevoked(true);
        repository.persist(token);
    }

    public void revokeAll(User user) {
        repository.revokeAllByUserId(user.getId());
    }

}
