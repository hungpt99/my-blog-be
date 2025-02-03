package com.hungpt.myblog.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Get the current authentication object from SecurityContextHolder
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        // If the authentication is not null and is an instance of User, return the username
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return Optional.of(user.getUsername());
        }

        // If no user is authenticated, return an empty Optional
        return Optional.empty();
    }
}
