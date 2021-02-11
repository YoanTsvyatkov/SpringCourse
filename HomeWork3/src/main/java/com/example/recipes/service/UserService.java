package com.example.recipes.service;

import com.example.recipes.model.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    User findUserById(Long id);
    List<User> getUsers();
    User addUser(@Valid User user);
    User deleteUser(Long id);
    User updateUser(@Valid User user);
    User getUserByUsername(String username);
    long getCountOfUsers();
}
