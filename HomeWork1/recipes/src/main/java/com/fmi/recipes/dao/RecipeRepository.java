package com.fmi.recipes.dao;

import com.fmi.recipes.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
}
