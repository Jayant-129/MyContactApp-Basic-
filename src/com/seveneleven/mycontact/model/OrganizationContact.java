// Represents an organization contact - UC6 adds setter methods for editing company name and website
package com.seveneleven.mycontact.model;

import java.util.UUID;

public class OrganizationContact extends Contact {

    private String companyName;
    private String website;

    public OrganizationContact(UUID ownerId, String companyName) {
        super(ownerId);
        if (companyName == null || companyName.trim().isEmpty())
            throw new IllegalArgumentException("Company name cannot be empty.");
        this.companyName = companyName.trim();
    }

    @Override
    public String getDisplayName() { return companyName; }

    public String getCompanyName() { return companyName; }
    public String getWebsite()     { return website; }

    // UC6 — setter methods to edit organisation details with validation
    public void setCompanyName(String companyName) {
        if (companyName == null || companyName.trim().isEmpty())
            throw new IllegalArgumentException("Company name cannot be empty.");
        this.companyName = companyName.trim();
    }

    public void setWebsite(String website) {
        this.website = (website == null) ? null : website.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (website != null && !website.isEmpty())
            sb.append("  Web   : ").append(website).append("\n");
        return sb.toString();
    }
}
