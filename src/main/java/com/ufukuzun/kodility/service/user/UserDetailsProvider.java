package com.ufukuzun.kodility.service.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@SuppressWarnings("UnusedDeclaration")
@Service
public class UserDetailsProvider implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = new User("user", "pass", Arrays.asList(
                new SimpleGrantedAuthority("ROLE_USER")
        ));

        return userDetails;
    }

}
