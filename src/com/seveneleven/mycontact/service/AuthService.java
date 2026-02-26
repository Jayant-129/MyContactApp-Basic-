// Handles user registration and login logic including validation and password hashing
package com.seveneleven.mycontact.service;

import com.seveneleven.mycontact.model.*;
import com.seveneleven.mycontact.repository.UserRepository;
import com.seveneleven.mycontact.session.SessionManager;
import com.seveneleven.mycontact.util.InputValidator;
import com.seveneleven.mycontact.util.PasswordHasher;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String name, String email, String password, String type) {
        InputValidator.requireNonEmpty(name, "Name");
        InputValidator.requireNonEmpty(password, "Password");

        if (!InputValidator.isValidEmail(email))
            throw new IllegalArgumentException("Invalid email format: " + email);

        if (userRepository.existsByEmail(email))
            throw new IllegalArgumentException("Email already registered: " + email);

        String hash = PasswordHasher.hash(password);
        User user;
        if ("premium".equalsIgnoreCase(type)) {
            user = new PremiumUser(name, email, hash);
        } else {
            user = new FreeUser(name, email, hash);
        }
        userRepository.save(user);
        System.out.println("Registered successfully: " + user);
        return user;
    }

    public boolean login(String email, String password) {
        return userRepository.findByEmail(email).map(user -> {
            if (PasswordHasher.matches(password, user.getPasswordHash())) {
                SessionManager.login(user);
                System.out.println("Welcome, " + user.getName() + "! (" + user.getUserType() + " account)");
                return true;
            } else {
                System.out.println("Incorrect password.");
                return false;
            }
        }).orElseGet(() -> {
            System.out.println("No account found with email: " + email);
            return false;
        });
    }

    public void logout() {
        if (SessionManager.isLoggedIn()) {
            System.out.println("Goodbye, " + SessionManager.getCurrentUser().getName() + "!");
            SessionManager.logout();
        } else {
            System.out.println("No user is currently logged in.");
        }
    }
}
