// Handles business logic for creating tags and applying or removing them from contacts
package com.seveneleven.mycontact.service;

import com.seveneleven.mycontact.model.Contact;
import com.seveneleven.mycontact.model.Tag;
import com.seveneleven.mycontact.repository.ContactRepository;
import com.seveneleven.mycontact.repository.TagRepository;
import com.seveneleven.mycontact.session.SessionManager;

import java.util.List;
import java.util.UUID;

public class TagService {

    private final TagRepository tagRepository;
    private final ContactRepository contactRepository;

    public TagService(TagRepository tagRepository, ContactRepository contactRepository) {
        this.tagRepository     = tagRepository;
        this.contactRepository = contactRepository;
    }

    public Tag createTag(String name) {
        requireSession();
        if (tagRepository.findByName(name).isPresent())
            throw new IllegalArgumentException("Tag already exists: " + name);
        Tag tag = new Tag(name);
        tagRepository.save(tag);
        System.out.println("Tag created: " + tag);
        return tag;
    }

    public void applyTagToContact(String tagName, UUID contactId) {
        Tag tag = getOrCreate(tagName);
        Contact contact = contactRepository.findById(contactId)
            .orElseThrow(() -> new IllegalArgumentException("Contact not found."));
        contact.addTag(tag);
        contactRepository.save(contact);
        System.out.println("Tag " + tag + " applied to " + contact.getDisplayName());
    }

    public void removeTagFromContact(String tagName, UUID contactId) {
        Tag tag = tagRepository.findByName(tagName)
            .orElseThrow(() -> new IllegalArgumentException("Tag not found: " + tagName));
        Contact contact = contactRepository.findById(contactId)
            .orElseThrow(() -> new IllegalArgumentException("Contact not found."));
        contact.removeTag(tag);
        contactRepository.save(contact);
        System.out.println("Tag " + tag + " removed from " + contact.getDisplayName());
    }

    public void listAllTags() {
        requireSession();
        List<Tag> tags = tagRepository.findAll();
        if (tags.isEmpty()) {
            System.out.println("No tags found.");
        } else {
            tags.forEach(System.out::println);
        }
    }

    public void deleteTag(String tagName) {
        requireSession();
        Tag tag = tagRepository.findByName(tagName)
            .orElseThrow(() -> new IllegalArgumentException("Tag not found: " + tagName));
        tagRepository.delete(tag.getTagId());
        System.out.println("Tag deleted: " + tag);
    }

    private Tag getOrCreate(String tagName) {
        return tagRepository.findByName(tagName).orElseGet(() -> {
            Tag t = new Tag(tagName);
            tagRepository.save(t);
            return t;
        });
    }

    private void requireSession() {
        if (!SessionManager.isLoggedIn())
            throw new IllegalStateException("You must be logged in.");
    }
}
