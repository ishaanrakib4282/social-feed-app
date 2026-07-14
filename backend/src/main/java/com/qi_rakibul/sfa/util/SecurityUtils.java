package com.qi_rakibul.sfa.util;

import com.qi_rakibul.sfa.application.domain.AuthenticatedUser;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static AuthenticatedUser currentUser() {

        return (AuthenticatedUser)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();
    }
}