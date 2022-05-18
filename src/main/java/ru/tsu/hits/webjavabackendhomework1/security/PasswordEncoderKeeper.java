package ru.tsu.hits.webjavabackendhomework1.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordEncoderKeeper {
    private static BCryptPasswordEncoder passwordEncoder;

    public static PasswordEncoder getPasswordEncoder() {
        if (passwordEncoder == null)
            passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder;
    }
}
