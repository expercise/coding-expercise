package com.expercise.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoder {

    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    public String encode(String password) {
        return shaPasswordEncoder.encodePassword(password, null);
    }

}
