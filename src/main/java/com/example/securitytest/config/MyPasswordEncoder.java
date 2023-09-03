package com.example.securitytest.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder {

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
