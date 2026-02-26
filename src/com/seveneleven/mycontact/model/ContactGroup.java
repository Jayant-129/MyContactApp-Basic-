// Represents a named group of contacts supporting bulk operations
package com.seveneleven.mycontact.model;

import java.util.*;

public class ContactGroup {

    private final UUID groupId;
    private final UUID ownerId;
    private String name;
    private final List<Contact> contacts;

    public ContactGroup(UUID ownerId, String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Group name cannot be empty.");
        this.groupId  = UUID.randomUUID();
        this.ownerId  = ownerId;
        this.name     = name.trim();
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        if (!contacts.contains(contact))
            contacts.add(contact);
    }

    public void removeContact(UUID contactId) {
        contacts.removeIf(c -> c.getContactId().equals(contactId));
    }

    public List<Contact> getContacts() {
        return Collections.unmodifiableList(contacts);
    }

    // Bulk operations
    public void bulkSoftDelete() {
        contacts.forEach(Contact::softDelete);
        System.out.println("Bulk soft-delete applied to group: " + name);
    }

    public void bulkAddTag(Tag tag) {
        contacts.forEach(c -> c.addTag(tag));
        System.out.println("Tag " + tag + " added to all contacts in group: " + name);
    }

    // Getters & setters
    public UUID getGroupId()  { return groupId; }
    public UUID getOwnerId()  { return ownerId; }
    public String getName()   { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Group name cannot be empty.");
        this.name = name.trim();
    }

    @Override
    public String toString() {
        return "Group: " + name + " (" + contacts.size() + " contact(s)) [ID: " + groupId + "]";
    }
}
