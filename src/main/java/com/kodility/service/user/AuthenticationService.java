package com.kodility.service.user;

import com.kodility.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthenticationService {

    @Autowired
    private UserService userService;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isNotAnonymousUser(authentication.getAuthorities())) {
            return userService.findByEmail(authentication.getName());
        }
        return null;
    }

    public boolean isCurrentUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return isNotAnonymousUser(authentication.getAuthorities());
    }

    public String getCurrentUsersEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public boolean isCurrentUserAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return hasRole(authentication.getAuthorities(), "ROLE_ADMIN");
    }

    private boolean isNotAnonymousUser(Collection<? extends GrantedAuthority> authorities) {
        return !hasRole(authorities, "ROLE_ANONYMOUS");
    }

    private boolean hasRole(Collection<? extends GrantedAuthority> authorities, String role) {
        return authorities.stream()
                .filter(a -> a.getAuthority().equals(role))
                .findFirst()
                .isPresent();
    }

}
