package com.expercise.service.user;

import com.expercise.domain.user.User;
import com.expercise.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    public User getCurrentUser() {
        return isCurrentUserAuthenticated() ? getOrFindCurrentUser() : null;
    }

    private User getOrFindCurrentUser() {
        CurrentUserHolder currentUserHolder = getCurrentUserHolder();
        if (currentUserHolder.hasNotCurrentUser()) {
            User currentUser = userService.findById(NumberUtils.parseLong(getAuthentication().getName()));
            currentUserHolder.setCurrentUser(currentUser);
        }
        return currentUserHolder.getCurrentUser();
    }

    public boolean isCurrentUserAuthenticated() {
        return !hasRole("ROLE_ANONYMOUS");
    }

    public boolean isCurrentUserAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    private boolean hasRole(String role) {
        return getAuthentication().getAuthorities().stream()
                .filter(a -> a.getAuthority().equals(role))
                .findFirst()
                .isPresent();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private CurrentUserHolder getCurrentUserHolder() {
        return applicationContext.getBean(CurrentUserHolder.class);
    }

    public void authenticate(String email, String password) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
