package com.fmi.reviews.service.impl;

import com.fmi.reviews.dao.UserRepository;
import com.fmi.reviews.exception.InvalidEntityDataException;
import com.fmi.reviews.exception.UnexistingEntityException;
import com.fmi.reviews.model.User;
import com.fmi.reviews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User deleteUser(Long id) {
       User user = getUser(id);
       userRepository.deleteById(id);
       return user;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UnexistingEntityException(String.format("User with id %s, does not exist",
                id)));
    }

    @Override
    public User addUser(User user) {
        user.setId(null);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new InvalidEntityDataException(String.format("Username %s is already taken", user.getUsername()));
        }

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User updateUser(Long id, User user) {
        if(!user.getId().equals(id)){
            throw new InvalidEntityDataException("User url id defers from body recipe id");
        }

        getUser(id);
        user.setModified(new Date());
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UnexistingEntityException(String.format("Username \"%s\" does not exist", username)));
    }

    @Override
    public long getUsersCount() {
        return userRepository.count();
    }
}
