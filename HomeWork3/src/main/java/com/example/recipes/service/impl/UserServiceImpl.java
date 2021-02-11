package com.example.recipes.service.impl;

import com.example.recipes.dao.UserRepository;
import com.example.recipes.exception.InvalidEntityDataException;
import com.example.recipes.exception.UnexistingEntityException;
import com.example.recipes.model.User;
import com.example.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UnexistingEntityException(String.format("Entity with id %d, does not exist", id)));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(@Valid User user) {
            user.setId(null);

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.saveAndFlush(user);
    }

    @Override
    public User deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
        return user;
    }

    @Override
    public User updateUser(@Valid User user) {
        User old = findUserById(user.getId());

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        user.setModified(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new InvalidEntityDataException(String.format("User with username %s, does not exist", username)));
    }

    @Override
    public long getCountOfUsers() {
        return userRepository.count();
    }


}
