// Represents a premium-tier user with access to advanced features like groups and bulk operations
package com.seveneleven.mycontact.model;

import java.time.LocalDate;

public class PremiumUser extends User {

    private LocalDate premiumSince;

    public PremiumUser(String name, String email, String passwordHash) {
        super(name, email, passwordHash, UserRole.PREMIUM);
        this.premiumSince = LocalDate.now();
    }

    @Override
    public String getUserType() {
        return "Premium";
    }

    public LocalDate getPremiumSince() {
        return premiumSince;
    }

    @Override
    public String toString() {
        return super.toString() + " | Premium since: " + premiumSince;
    }
}
