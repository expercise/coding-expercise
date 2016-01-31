package com.expercise.service.user;

import com.expercise.domain.user.SocialUserDetails;
import com.expercise.domain.user.User;
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

        throw new UsernameNotFoundException("No user found with email: " + email);
    }

    public UserDetails loadUserByUserId(String userId) {
        User user = userService.findById(Long.parseLong(userId));

        if (user != null) {
            return createUserDetailsFrom(user);
        }

        throw new UsernameNotFoundException("No user found with user id: " + userId);
    }

    private UserDetails createUserDetailsFrom(User user) {
        SocialUserDetails socialUserDetails = new SocialUserDetails(user.getId().toString(), user.getPassword(), getAuthorities(user));

        socialUserDetails.setId(user.getId());
        socialUserDetails.setFirstName(user.getFirstName());
        socialUserDetails.setLastName(user.getLastName());

        return socialUserDetails;
    }

    private List<GrantedAuthority> getAuthorities(User user) {
        return Arrays.asList(user.getUserRole().getAuthorities()).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
