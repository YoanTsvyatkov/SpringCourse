package com.fmi.recipes.web;

import com.fmi.recipes.exception.InvalidEntityDataException;
import com.fmi.recipes.model.Credentials;
import com.fmi.recipes.model.JwtResponse;
import com.fmi.recipes.model.Role;
import com.fmi.recipes.model.User;
import com.fmi.recipes.service.UserService;
import com.fmi.recipes.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.fmi.recipes.util.ErrorHandlingUtils.getViolationsAsStringList;

@RestController
@Slf4j
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/api/login")
    public JwtResponse login(@Valid @RequestBody Credentials credentials, Errors errors) {
        if(errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid username or password", getViolationsAsStringList(errors));
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getUsername(), credentials.getPassword()));
        final User user = userService.getUserByUsername(credentials.getUsername());
        final String token = jwtUtils.generateToken(user);
        return new JwtResponse(user, token);
    }

    @PostMapping("/api/register")
    public User register(@Valid @RequestBody User user, Errors errors){
        if(errors.hasErrors()){
            throw new InvalidEntityDataException("Invalid username or password" , getViolationsAsStringList(errors));
        }

        user.setRole(Role.USER);
        return userService.addUser(user);
    }
}
