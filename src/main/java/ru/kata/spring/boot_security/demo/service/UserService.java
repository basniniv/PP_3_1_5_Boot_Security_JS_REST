package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {

    public List<User> getUsers();

    public User getUser(long id);

    public void deleteUser(long id);
}
