// In-memory repository for storing and retrieving ContactGroup objects by UUID
package com.seveneleven.mycontact.repository;

import com.seveneleven.mycontact.model.ContactGroup;

import java.util.*;
import java.util.stream.Collectors;

public class GroupRepository {

    private final Map<UUID, ContactGroup> store = new HashMap<>();

    public void save(ContactGroup group) {
        store.put(group.getGroupId(), group);
    }

    public Optional<ContactGroup> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<ContactGroup> findByOwner(UUID ownerId) {
        return store.values().stream()
            .filter(g -> g.getOwnerId().equals(ownerId))
            .collect(Collectors.toList());
    }

    public void delete(UUID id) {
        store.remove(id);
    }
}
