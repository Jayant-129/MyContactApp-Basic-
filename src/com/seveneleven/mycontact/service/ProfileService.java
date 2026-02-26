// Handles updating the logged-in user's profile fields like name, email, and password
package com.seveneleven.mycontact.service;

import com.seveneleven.mycontact.model.User;
import com.seveneleven.mycontact.repository.UserRepository;
import com.seveneleven.mycontact.session.SessionManager;
import com.seveneleven.mycontact.util.InputValidator;
import com.seveneleven.mycontact.util.PasswordHasher;

public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void updateName(String newName) {
        User user = requireSession();
        InputValidator.requireNonEmpty(newName, "Name");
        user.setName(newName.trim());
        userRepository.save(user);
        System.out.println("Name updated to: " + user.getName());
    }

    public void updateEmail(String newEmail) {
        User user = requireSession();
        if (!InputValidator.isValidEmail(newEmail))
            throw new IllegalArgumentException("Invalid email format.");
        if (userRepository.existsByEmail(newEmail))
            throw new IllegalArgumentException("Email already in use: " + newEmail);
        user.setEmail(newEmail.trim());
        userRepository.save(user);
        System.out.println("Email updated to: " + user.getEmail());
    }

    public void changePassword(String oldPassword, String newPassword) {
        User user = requireSession();
        if (!PasswordHasher.matches(oldPassword, user.getPasswordHash()))
            throw new IllegalArgumentException("Old password is incorrect.");
        InputValidator.requireNonEmpty(newPassword, "New password");
        user.setPasswordHash(PasswordHasher.hash(newPassword));
        userRepository.save(user);
        System.out.println("Password changed successfully.");
    }

    public void showProfile() {
        User user = requireSession();
        System.out.println("=== My Profile ===");
        System.out.println(user);
    }

    private User requireSession() {
        if (!SessionManager.isLoggedIn())
            throw new IllegalStateException("You must be logged in.");
        return SessionManager.getCurrentUser();
    }
}
