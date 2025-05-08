package supportapp.service;

import supportapp.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getByUsername(String username);
    Optional<User> getById(Long id);
    User save(User user);
}
