package com.ufukuzun.kodility.service.user;

import com.ufukuzun.kodility.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsProvider implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findByEmail(email);

        if (user != null) {
            return createUserDetailsFrom(user);
        }

        throw new UsernameNotFoundException("User not found");
    }

    private UserDetails createUserDetailsFrom(User user) {
        List<GrantedAuthority> authorities = getAuthorities(user);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> getAuthorities(User user) {
        return Arrays.asList(user.getUserRole().getAuthorities()).stream()
                .map(a -> new SimpleGrantedAuthority(a))
                .collect(Collectors.toList());
    }

}
