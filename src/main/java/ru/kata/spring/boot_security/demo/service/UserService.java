package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    public List<User> getUsers();

    public User getUser(long id);

    public void deleteUser(long id);

    void update(User updatedUser);

    void saveUser(User user);
}
