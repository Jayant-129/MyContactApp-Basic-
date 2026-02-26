// In-memory repository for storing and retrieving Tag objects by name and UUID
package com.seveneleven.mycontact.repository;

import com.seveneleven.mycontact.model.Tag;

import java.util.*;
import java.util.stream.Collectors;

public class TagRepository {

    private final Map<UUID, Tag> store = new HashMap<>();

    public void save(Tag tag) {
        store.put(tag.getTagId(), tag);
    }

    public Optional<Tag> findByName(String name) {
        return store.values().stream()
            .filter(t -> t.getName().equalsIgnoreCase(name.trim()))
            .findFirst();
    }

    public Optional<Tag> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<Tag> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(UUID id) {
        store.remove(id);
    }
}
