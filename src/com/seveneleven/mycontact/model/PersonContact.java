// Represents a personal contact - UC6 adds setter methods for editing name and birthday
package com.seveneleven.mycontact.model;

import java.time.LocalDate;
import java.util.UUID;

public class PersonContact extends Contact {

    private String firstName;
    private String lastName;
    private LocalDate birthday;

    public PersonContact(UUID ownerId, String firstName, String lastName) {
        super(ownerId);
        if (firstName == null || firstName.trim().isEmpty())
            throw new IllegalArgumentException("First name cannot be empty.");
        this.firstName = firstName.trim();
        this.lastName  = (lastName == null) ? "" : lastName.trim();
    }

    @Override
    public String getDisplayName() {
        return firstName + (lastName.isEmpty() ? "" : " " + lastName);
    }

    public String    getFirstName() { return firstName; }
    public String    getLastName()  { return lastName; }
    public LocalDate getBirthday()  { return birthday; }

    // UC6 — setter methods to edit person details with validation
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty())
            throw new IllegalArgumentException("First name cannot be empty.");
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = (lastName == null) ? "" : lastName.trim();
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (birthday != null)
            sb.append("  DOB   : ").append(birthday).append("\n");
        return sb.toString();
    }
}
