package com.fmi.recipes.init;

import com.fmi.recipes.dao.RecipeRepository;
import com.fmi.recipes.model.Recipe;
import com.fmi.recipes.model.Role;
import com.fmi.recipes.model.Status;
import com.fmi.recipes.model.User;
import com.fmi.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserService userService;

    private static List<Recipe> recipes = List.of(
            new Recipe("admin", "Caramel Cake",
                    "Moist, delicious layer cake with caramel icing.",
                    37,
                    List.of("3 cups white sugar", "1 ½ cups butter",
                            "5 eggs",
                            "¼ teaspoon salt"),
                    "https://pixabay.com/photos/tree-sunset-amazing-beautiful-736885/",
                    "1019 calories; protein 8.6g 17% DV; carbohydrates 154.5g 50% DV; fat 42.7g 66% DV; cholesterol 185.8mg 62% DV; sodium 455.4mg 18% DV."
            ),
            new Recipe("Test", "Test",
                    "test",
                    37,
                    List.of("test1",
                            "test2",
                            "test3"),
                    "https://pixabay.com/photos/tree-sunset-amazing-beautiful-736885/",
                    "1019 calories; protein 8.6g 17% DV; carbohydrates 154.5g 50% DV; fat 42.7g 66% DV; cholesterol 185.8mg 62% DV; sodium 455.4mg 18% DV."
            )
    );

    private static List<User> users = List.of(
            new User("admin",
                    "adm3in****",
                    "testname",
                    Role.ADMIN,
                    "https://pixabay.com/photos/tree-sunset-amazing-beautiful-736885/",
                    "admin",
                    Status.ACTIVE
            ),
            new User("user",
                    "use3r****",
                    "tes321tname",
                    Role.USER,
                    "https://pixabay.com/photos/tree-sunset-amazing-beautiful-736885/",
                    "user",
                    Status.ACTIVE
            )
    );

    @Override
    public void run(String... args) throws Exception {
        if (recipeRepository.count() == 0) {
            recipes.forEach(recipe -> recipeRepository.save(recipe));
        }

        if (userService.getSize() == 0) {
            users.forEach(user -> userService.addUser(user));
        }
    }
}
