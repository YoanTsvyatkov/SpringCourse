package com.example.recipes.service;

import com.example.recipes.model.Recipe;
import org.springframework.validation.Errors;

import javax.validation.Valid;
import java.util.List;

public interface RecipeService {
    Recipe findRecipeById(Long id);
    List<Recipe> getRecipes();
    Recipe addRecipe(@Valid Recipe user);
    Recipe deleteRecipe(Long id);
    Recipe updateRecipe(@Valid Recipe recipe);
    long getCountOfRecipes();
}
