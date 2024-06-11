package infrastructure.repositories;

import domain.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public User findByUsername(String username) {
        return users.get(username);
    }

    @Override
    public boolean exists(String username) {
        return users.containsKey(username);
    }

    @Override
    public void delete(String username) {
        users.remove(username);
    }
}