package com.fmi.recipes.service.impl;

import com.fmi.recipes.dao.RecipeRepository;
import com.fmi.recipes.exception.InvalidEntityDataException;
import com.fmi.recipes.exception.InvalidOperationException;
import com.fmi.recipes.exception.UnexistingEntityException;
import com.fmi.recipes.model.Recipe;
import com.fmi.recipes.model.Role;
import com.fmi.recipes.model.User;
import com.fmi.recipes.service.RecipeService;
import com.fmi.recipes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    private RecipeRepository recipeRepository;
    private UserService userService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, UserService userService) {
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    @Override
    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipe.setId(null);

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(name);
        recipe.setUserId(user.getId());
        return recipeRepository.insert(recipe);
    }

    @Override
    public Recipe getRecipeById(String id) {
        return recipeRepository.findById(id).orElseThrow(() -> new UnexistingEntityException(String.format("Recipe with id %s does not exist", id)));
    }

    @Override
    public Recipe deleteRecipe(String id) {
        Recipe recipe = getRecipeById(id);

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(name);

        if(user.getRole() == Role.USER && !user.getId().equals(recipe.getUserId())){
            throw new InvalidOperationException("Unauthorised deletion");
        }

        recipeRepository.delete(recipe);
        return recipe;
    }

    @Override
    public Recipe updateRecipe(String id, Recipe recipe) {
        if (!id.equals(recipe.getId())) {
            throw new InvalidEntityDataException("Recipe url id defers from body recipe id");
        }

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(name);

        if(user.getRole() == Role.USER && !user.getId().equals(recipe.getUserId())){
            throw new InvalidOperationException("Unauthorised update");
        }

        getRecipeById(id);
        recipe.setModified(new Date());
        return recipeRepository.save(recipe);
    }

    @Override
    public long getSize() {
        return recipeRepository.count();
    }
}
