package com.ufukuzun.kodility.service.user;

import com.ufukuzun.kodility.domain.user.User;
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

    private boolean isNotAnonymousUser(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if (!authority.getAuthority().equals("ROLE_ANONYMOUS")) {
                return true;
            }
        }
        return false;
    }

}
