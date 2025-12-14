package com.sharom.repository;

import com.sharom.entity.RefreshToken;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RefreshTokenRepository implements PanacheRepository<RefreshToken> {

    public RefreshToken findValidToken(String token){
        return find("token = ?1 and revoked = false", token).firstResult();
    }


    public void revokeAllByUserId(Long userId){
        update("revoke = true where user.id = ?1", userId);
    }
}
