package com.app.phone_book.services;

import com.app.phone_book.models.*;
import com.app.phone_book.repositories.RoleRepository;
import com.app.phone_book.repositories.UserRepository;
import com.app.phone_book.validators.RegisterForm;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user, UUID roleId) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        Role role = roleRepository.findById(roleId);

        user.setPassword(encodedPassword);
        user.setRole(role);
        return userRepository.save(user);
    }

    public User edit(User user, UUID roleId, String password) {

        Role role = roleRepository.findById(roleId);

        if(password != null && !password.isEmpty()) {
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }

        user.setRole(role);
        return userRepository.save(user);
    }

    public User register(RegisterForm registerForm) {
        // Check if user with the same email already exists
        if (userRepository.findByEmail(registerForm.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use!");
        }

        // Hash the password
        String encodedPassword = passwordEncoder.encode(registerForm.getPassword());
        Role role = roleRepository.findByName(String.valueOf(UserRole.USER));

        // Create a new user
        User user = new User();
        user.setEmail(registerForm.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(role);

        // Save the user in the database
        return userRepository.save(user);
    }

    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    public Page<User> getAllUsers(int page, int size) {
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<User> getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        return Optional.ofNullable(user);
    }
}
