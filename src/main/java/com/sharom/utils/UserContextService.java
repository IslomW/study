package com.sharom.utils;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserContextService {

    @Inject
    SecurityIdentity securityIdentity;

    public Long getUserId() {
        // допустим, в JWT claim "sub" хранится ID пользователя
        return Long.valueOf(securityIdentity.getPrincipal().getName());
    }

    public String getLanguage() {
        // допустим, язык хранится в кастомном claim "lang"
        Object lang = securityIdentity.getAttribute("lang");
        return lang != null ? lang.toString() : "en";
    }
}
