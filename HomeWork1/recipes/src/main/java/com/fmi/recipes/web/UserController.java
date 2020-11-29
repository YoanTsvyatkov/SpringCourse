package com.fmi.recipes.web;

import com.fmi.recipes.exception.InvalidEntityDataException;
import com.fmi.recipes.model.Recipe;
import com.fmi.recipes.model.User;
import com.fmi.recipes.service.RecipeService;
import com.fmi.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static com.fmi.recipes.util.ErrorHandlingUtils.getViolationsAsStringList;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping
    ResponseEntity<User> addUser(@Valid @RequestBody User user, Errors errors){
        if(errors.hasErrors()){
            throw new InvalidEntityDataException("Invalid user data", getViolationsAsStringList(errors));
        }

        User newUser = userService.addUser(user);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(newUser.getId()).toUri()
        ).body(newUser);
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable String id){
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    User deleteUserById(@PathVariable String id){
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    User updateUser(@PathVariable String id, @Valid @RequestBody User user, Errors errors){
        if(errors.hasErrors()){
            throw new InvalidEntityDataException("Invalid user data", getViolationsAsStringList(errors));
        }

        return userService.updateUser(id, user);
    }
}
