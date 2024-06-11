package infrastructure.repositories;

import domain.User;

public interface UserRepository {
    void save(User user);

    User findByUsername(String username);

    boolean exists(String username);

    void delete(String username);
}