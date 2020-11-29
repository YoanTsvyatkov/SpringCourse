package com.fmi.recipes.service;

import com.fmi.recipes.model.Recipe;
import com.fmi.recipes.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User addUser(User user);
    User getUserById(String id);
    User deleteUser(String id);
    User updateUser(String id, User user);
    User getUserByUsername(String username);
    long getSize();
}
