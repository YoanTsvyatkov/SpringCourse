package com.fmi.recipes.service;

import com.fmi.recipes.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipes();
    Recipe addRecipe(Recipe recipe);
    Recipe getRecipeById(String id);
    Recipe deleteRecipe(String id);
    Recipe updateRecipe(String id, Recipe recipe);
    long getSize();
}
