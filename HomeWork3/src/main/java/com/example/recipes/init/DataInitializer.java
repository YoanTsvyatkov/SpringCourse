package com.example.recipes.init;

import com.example.recipes.model.Recipe;
import com.example.recipes.model.Role;
import com.example.recipes.model.User;
import com.example.recipes.service.RecipeService;
import com.example.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    UserService userService;

    @Autowired
    RecipeService recipeService;

    private static final List<User> users = List.of(new User("admin", "admin"
                    , "admin2*aaa", Role.ADMIN, "admin"),
            new User("Ivan Ivanov", "ivan", "1234*aaaa1", Role.USER,
                    "user"));

    private static final List<Recipe> recipes = List.of(new Recipe("Cake", "Cake description",20, "full description"),
            new Recipe("Cake1", "Cake description1", 30, "full description1"));


    @Override
    public void run(String... args) throws Exception {
        if (userService.getCountOfUsers() == 0) {
            users.forEach(userService::addUser);
        }
        if (recipeService.getCountOfRecipes() == 0) {
            recipes.forEach(r -> r.setCreator(users.get(0)));
            recipes.forEach(recipeService::addRecipe);
        }
    }
}
