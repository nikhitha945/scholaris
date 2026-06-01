package com.scholaris.config;

import com.scholaris.entity.User;
import com.scholaris.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            return;
        }

        userRepository.save(new User(
                "admin",
                "admin@scholaris.example",
                passwordEncoder.encode("admin123"),
                "System Administrator",
                User.Role.ADMIN));

        userRepository.save(new User(
                "teacher",
                "teacher@scholaris.example",
                passwordEncoder.encode("teacher123"),
                "Jane Teacher",
                User.Role.TEACHER));

        log.info("Seeded demo users: admin/admin123, teacher/teacher123");
    }
}
