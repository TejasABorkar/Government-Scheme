package com.govermentscheme.backend.common.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHashGenerator implements CommandLineRunner {

    @Override
    public void run(String... args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("Admin@123");
        System.out.println("========================================");
        System.out.println("ADMIN BCrypt HASH: " + hash);
        System.out.println("Verify: " + encoder.matches("Admin@123", hash));
        System.out.println("========================================");
    }
}