package com.example.recipes.dao;

import com.example.recipes.model.Recipe;
import com.example.recipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
