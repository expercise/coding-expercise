package com.expercise.service.user;

import com.expercise.domain.user.User;
import com.expercise.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.Connection;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private UserDetailsProvider userDetailsProvider;

    public User getCurrentUser() {
        return isCurrentUserAuthenticated() ? getOrFindCurrentUser() : null;
    }

    private User getOrFindCurrentUser() {
        CurrentUserHolder currentUserHolder = getCurrentUserHolder();
        if (currentUserHolder.hasNotCurrentUser()) {
            User currentUser = getAuthenticatedUserByAuthenticationName();
            currentUserHolder.setCurrentUser(currentUser);
        }
        return currentUserHolder.getCurrentUser();
    }

    private User getAuthenticatedUserByAuthenticationName() {
        String authenticationName = getAuthentication().getName();
        if (StringUtils.isNumeric(authenticationName)) {
            return userService.findById(NumberUtils.parseLong(authenticationName));
        } else {
            // If auto-authenticated after registration, find by email
            return userService.findByEmail(authenticationName);
        }
    }

    public boolean isCurrentUserAuthenticated() {
        return !hasRole("ROLE_ANONYMOUS");
    }

    public boolean isCurrentUserAdmin() {
        return hasRole("ROLE_ADMIN");
    }

    private boolean hasRole(String role) {
        return getAuthentication().getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(role));
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

    public void authenticate(Connection<?> connection, User user) {
        UserDetails userDetails = userDetailsProvider.loadUserByUserId(user.getId().toString());
        Authentication authentication = new SocialAuthenticationToken(connection, userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
