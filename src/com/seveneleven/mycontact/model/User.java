// Abstract base class representing a user in the MyContacts system
package com.seveneleven.mycontact.model;

import java.util.UUID;

public abstract class User {

    private UUID userId;
    private String name;
    private String email;
    private String passwordHash;
    private UserRole role;

    public User(String name, String email, String passwordHash, UserRole role) {
        this.userId       = UUID.randomUUID();
        this.name         = name;
        this.email        = email;
        this.passwordHash = passwordHash;
        this.role         = role;
    }

    public abstract String getUserType();

    // Getters
    public UUID getUserId()        { return userId; }
    public String getName()        { return name; }
    public String getEmail()       { return email; }
    public String getPasswordHash(){ return passwordHash; }
    public UserRole getRole()      { return role; }

    // Setters with basic validation
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty())
            throw new IllegalArgumentException("Email cannot be empty.");
        this.email = email.trim();
    }

    public void setPasswordHash(String passwordHash) {
        if (passwordHash == null || passwordHash.isEmpty())
            throw new IllegalArgumentException("Password hash cannot be empty.");
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s <%s> (ID: %s)", getUserType(), name, email, userId);
    }
}
