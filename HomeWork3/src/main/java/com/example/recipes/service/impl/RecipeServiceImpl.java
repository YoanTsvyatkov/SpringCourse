package com.example.recipes.service.impl;

import com.example.recipes.dao.RecipeRepository;
import com.example.recipes.exception.UnauthorisedModificationException;
import com.example.recipes.exception.UnexistingEntityException;
import com.example.recipes.model.Recipe;
import com.example.recipes.model.User;
import com.example.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;


    @Override
    public Recipe findRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new UnexistingEntityException(String.format("Entity with id %d, does not exist", id)));
    }

    @Override
    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe addRecipe(@Valid Recipe recipe) {
        recipe.setId(null);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            User user = (User) authentication.getPrincipal();
            recipe.setCreator(user);
        }

        return recipeRepository.saveAndFlush(recipe);
    }

    @Override
    public Recipe deleteRecipe(Long id) {
        Recipe recipe = findRecipeById(id);

        recipeRepository.delete(recipe);
        return recipe;
    }

    @Override
    public Recipe updateRecipe(@Valid Recipe recipe) {
        Recipe old = findRecipeById(recipe.getId());

        recipe.setModified(LocalDateTime.now());
        return recipeRepository.saveAndFlush(recipe);
    }

    @Override
    public long getCountOfRecipes() {
        return recipeRepository.count();
    }
}
