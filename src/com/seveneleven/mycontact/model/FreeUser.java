// Represents a free-tier user with basic contact management access
package com.seveneleven.mycontact.model;

public class FreeUser extends User {

    public FreeUser(String name, String email, String passwordHash) {
        super(name, email, passwordHash, UserRole.FREE);
    }

    @Override
    public String getUserType() {
        return "Free";
    }
}
