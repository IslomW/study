package com.sharom.utils;//package com.sharom.utils;
//
//import io.quarkus.security.identity.SecurityIdentity;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.inject.Provider;
//
//@ApplicationScoped
//public class AuditService {
//
//
//    @Inject
//    Provider<SecurityIdentity> identityProvider;
//
//    public Long getCurrentUserId() {
//        SecurityIdentity identity = identityProvider.get();
//
//        if (identity.isAnonymous()) {
//
//
//            // Возвращаем null или специальный ID для анонимных действий (e.g., -1)
//            return null;
//        }
//
//        String principalName = identity.getPrincipal().getName();
//        if (principalName == null || principalName.isBlank()) {
//            return null;
//        }
//
//        try {
//            return Long.valueOf(principalName);
//        } catch (NumberFormatException e) {
//            // Log the error: the principal name is not a valid Long ID
//            return null;
//        }
//    }
//}
