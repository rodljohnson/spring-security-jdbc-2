package org.example;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String user = "user";
        System.out.println(encoder.encode(user));
        String admin = "admin";
        System.out.println(encoder.encode(admin));
        String pass = "pass";
        System.out.println(encoder.encode(pass));
    }
}
