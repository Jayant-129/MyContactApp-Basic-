// In-memory repository for storing and retrieving User objects by UUID
package com.seveneleven.mycontact.repository;

import com.seveneleven.mycontact.model.User;

import java.util.*;

public class UserRepository {

    private final Map<UUID, User> store = new HashMap<>();

    public void save(User user) {
        store.put(user.getUserId(), user);
    }

    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return store.values().stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email))
            .findFirst();
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(UUID id) {
        store.remove(id);
    }
}
