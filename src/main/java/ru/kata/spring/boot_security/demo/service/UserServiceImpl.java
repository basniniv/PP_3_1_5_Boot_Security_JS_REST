package ru.kata.spring.boot_security.demo.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user.get();
    }


    public User getUser(long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Hibernate.initialize(user.getRoles());
        return user;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void update(User updatedUser) {
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userRepository.save(updatedUser);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }


}

