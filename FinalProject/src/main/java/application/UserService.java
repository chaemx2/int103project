package application;

import domain.User;
import infrastructure.repositories.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(String username, String password, boolean isAdmin) {
        if (userRepository.exists(username)) {
            throw new RuntimeException("This username is already used");
        }
        User user = new User(username, password, isAdmin);
        userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Your username or password is incorrect");
        }
        return user;
    }

    public void deleteUser(String username) {
        userRepository.delete(username);
    }

}