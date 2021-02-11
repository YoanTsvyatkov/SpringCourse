package com.fmi.recipes.web;

import com.fmi.recipes.exception.InvalidEntityDataException;
import com.fmi.recipes.model.Recipe;
import com.fmi.recipes.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static com.fmi.recipes.util.ErrorHandlingUtils.getViolationsAsStringList;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping
    List<Recipe> getRecipes() {
        return recipeService.getRecipes();
    }

    @PostMapping
    ResponseEntity<Recipe> addRecipe(@Valid @RequestBody Recipe recipe, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid recipe data", getViolationsAsStringList(errors));
        }

        Recipe newRecipe = recipeService.addRecipe(recipe);

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
                        .buildAndExpand(newRecipe.getId()).toUri()
        ).body(newRecipe);
    }

    @GetMapping("/{id}")
    Recipe getRecipeById(@PathVariable String id) {
        return recipeService.getRecipeById(id);
    }

    @DeleteMapping("/{id}")
    Recipe deletePostById(@PathVariable String id) {
        return recipeService.deleteRecipe(id);
    }

    @PutMapping("/{id}")
    Recipe putRecipeById(@PathVariable String id, @Valid @RequestBody Recipe recipe, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidEntityDataException("Invalid recipe data", getViolationsAsStringList(errors));
        }

        return recipeService.updateRecipe(id, recipe);
    }
}
