// Provides business logic for creating, managing, and performing bulk operations on contact groups
package com.seveneleven.mycontact.service;

import com.seveneleven.mycontact.model.*;
import com.seveneleven.mycontact.repository.ContactRepository;
import com.seveneleven.mycontact.repository.GroupRepository;
import com.seveneleven.mycontact.session.SessionManager;

import java.util.List;
import java.util.UUID;

public class GroupService {

    private final GroupRepository groupRepository;
    private final ContactRepository contactRepository;

    public GroupService(GroupRepository groupRepository, ContactRepository contactRepository) {
        this.groupRepository   = groupRepository;
        this.contactRepository = contactRepository;
    }

    public ContactGroup createGroup(String name) {
        requireSession();
        UUID ownerId = SessionManager.getCurrentUser().getUserId();
        ContactGroup group = new ContactGroup(ownerId, name);
        groupRepository.save(group);
        System.out.println("Group created: " + group.getName());
        return group;
    }

    public void addContactToGroup(UUID groupId, UUID contactId) {
        ContactGroup group = getOwnedGroup(groupId);
        Contact contact = contactRepository.findById(contactId)
            .orElseThrow(() -> new IllegalArgumentException("Contact not found."));
        group.addContact(contact);
        groupRepository.save(group);
        System.out.println("Added " + contact.getDisplayName() + " to group " + group.getName());
    }

    public void removeContactFromGroup(UUID groupId, UUID contactId) {
        ContactGroup group = getOwnedGroup(groupId);
        group.removeContact(contactId);
        groupRepository.save(group);
        System.out.println("Contact removed from group.");
    }

    public void bulkTag(UUID groupId, Tag tag) {
        ContactGroup group = getOwnedGroup(groupId);
        group.bulkAddTag(tag);
        group.getContacts().forEach(contactRepository::save);
    }

    public void bulkSoftDelete(UUID groupId) {
        ContactGroup group = getOwnedGroup(groupId);
        group.bulkSoftDelete();
        group.getContacts().forEach(contactRepository::save);
    }

    public void listGroups() {
        requireSession();
        UUID ownerId = SessionManager.getCurrentUser().getUserId();
        List<ContactGroup> groups = groupRepository.findByOwner(ownerId);
        if (groups.isEmpty()) {
            System.out.println("No groups found.");
        } else {
            groups.forEach(System.out::println);
        }
    }

    public void showGroup(UUID groupId) {
        ContactGroup group = getOwnedGroup(groupId);
        System.out.println(group);
        group.getContacts().forEach(c -> System.out.println("  - " + c.getDisplayName()));
    }

    public void deleteGroup(UUID groupId) {
        getOwnedGroup(groupId);
        groupRepository.delete(groupId);
        System.out.println("Group deleted.");
    }

    private ContactGroup getOwnedGroup(UUID groupId) {
        requireSession();
        UUID ownerId = SessionManager.getCurrentUser().getUserId();
        ContactGroup g = groupRepository.findById(groupId)
            .orElseThrow(() -> new IllegalArgumentException("Group not found: " + groupId));
        if (!g.getOwnerId().equals(ownerId))
            throw new IllegalStateException("Access denied to this group.");
        return g;
    }

    private void requireSession() {
        if (!SessionManager.isLoggedIn())
            throw new IllegalStateException("You must be logged in.");
    }
}
