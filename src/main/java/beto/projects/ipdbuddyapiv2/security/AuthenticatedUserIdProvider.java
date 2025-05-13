package beto.projects.ipdbuddyapiv2.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticatedUserIdProvider {

    public String getAuthenticatedUserId() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElseThrow(() -> new IllegalStateException("No authenticated user found in security context"));
    }
}
