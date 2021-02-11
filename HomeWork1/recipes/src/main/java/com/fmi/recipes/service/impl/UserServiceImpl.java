package com.fmi.recipes.service.impl;

import com.fmi.recipes.dao.RecipeRepository;
import com.fmi.recipes.dao.UserRepository;
import com.fmi.recipes.exception.InvalidEntityDataException;
import com.fmi.recipes.exception.UnexistingEntityException;
import com.fmi.recipes.model.Recipe;
import com.fmi.recipes.model.User;
import com.fmi.recipes.service.RecipeService;
import com.fmi.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Value("${male.url}")
    private String maleUrl;

    @Value("${female.url}")
    private String femaleUrl;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        user.setId(null);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new InvalidEntityDataException(String.format("Username %s is already taken", user.getUsername()));
        }

        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new InvalidEntityDataException(String.format("Name %s is already taken", user.getName()));
        }

        if (user.getGender() == null) {
            user.setGender("Male");
        }

        if (user.getPhoto() == null) {
            user.setPhoto((user.getGender().equals("Male") ? maleUrl : femaleUrl));
        }

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.insert(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UnexistingEntityException(String.format("User with id %s does not exist", id)));
    }

    @Override
    public User deleteUser(String id) {
        User user = getUserById(id);
        userRepository.delete(user);
        return user;
    }

    @Override
    public User updateUser(String id, User user) {
        if (!id.equals(user.getId())) {
            throw new InvalidEntityDataException("User url id defers from body recipe id");
        }

        getUserById(id);
        user.setModified(new Date());
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new InvalidEntityDataException(String.format(
                "No user with username: %s", username)));
    }

    @Override
    public long getSize() {
        return userRepository.count();
    }
}
