package com.app.phone_book.migrations;

import com.app.phone_book.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DBInit {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner runSqlScripts() {
        return args -> {
            insertInitialData();
        };
    }

    private void insertInitialData() {
        for (UserRole role : UserRole.values()) {
            String insertRole = "INSERT INTO roles (id, name, created_at, updated_at) VALUES (SYS_GUID(), ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
            jdbcTemplate.update(insertRole, role.name());
        }

        String insertUser = "INSERT INTO users (id, password, email, created_at, updated_at, role_id) " +
                "VALUES (SYS_GUID(),?, 'john@example.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, (SELECT id FROM roles WHERE name = 'ADMIN'))";
        jdbcTemplate.update(insertUser, passwordEncoder.encode("password123"));
    }
}