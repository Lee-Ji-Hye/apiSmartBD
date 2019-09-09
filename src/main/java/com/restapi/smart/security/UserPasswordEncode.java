package com.restapi.smart.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserPasswordEncode implements PasswordEncoder {

//    @Override
//    public String encode(CharSequence rawPassword) {
//        return (String) rawPassword;
//    }
//
//    @Override
//    public boolean matches(CharSequence rawPassword, String encodedPassword) {
//        System.out.println("encodedPassword : "+encodedPassword+"  /  rawPassword:"+rawPassword);
//        return rawPassword.equals(encodedPassword);
//    }

    private PasswordEncoder passwordEncoder;
    public UserPasswordEncode() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    public UserPasswordEncode(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}