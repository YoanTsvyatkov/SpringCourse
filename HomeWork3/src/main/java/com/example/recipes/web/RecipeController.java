package com.example.recipes.web;

import com.example.recipes.model.Recipe;
import com.example.recipes.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

@Controller
@Slf4j
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @Value("${uploads.directory}")
    private String uploadsDir;

    @GetMapping("/recipes")
    String getRecipes(Model model, Authentication auth) {
        model.addAttribute("recipes", recipeService.getRecipes());
        model.addAttribute("path", "recipes");
        return "recipes";
    }

    @GetMapping("/recipe-form")
    ModelAndView getRecipeForm(
            @ModelAttribute("recipe") Recipe recipe,
            @RequestParam(value = "mode", required = false) String mode,
            @RequestParam(value = "recipeId", required = false) Long recipeId) {
        ModelAndView result = new ModelAndView("recipe-form");

        if ("edit".equals(mode)) {
            Recipe found = recipeService.findRecipeById(recipeId);
            result.addObject("recipe", found);
        } else {
            result.addObject("recipe", new Recipe());
        }

        return result;
    }

    @PostMapping(value = "/recipes", params = "edit")
    public String editRecipe(@RequestParam("edit") Long editId,
                             Model model, UriComponentsBuilder uriBuilder) {
        URI uri = uriBuilder.path("/recipe-form")
                .query("mode=edit&recipeId={id}").buildAndExpand(editId).toUri();
        return "redirect:" + uri.toString();
    }

    @PostMapping(value = "/recipes", params = "delete")
    public String deleteRecipe(@RequestParam("delete") Long deleteId, Model model) {
        Recipe old = recipeService.deleteRecipe(deleteId);
        handleFile(null, old);
        return "redirect:/recipes";
    }

    @PostMapping("/recipe-form")
    String addRecipe(@Valid @ModelAttribute("recipe") Recipe recipe,
                     BindingResult errors,
                     @RequestParam(value = "file") MultipartFile file,
                     Model model) {

        model.addAttribute("fileError", null);

        if (errors.hasErrors()) {
            return "recipe-form";
        }

        if (file != null && !file.isEmpty() && file.getOriginalFilename().length() > 4) {
            if (Pattern.matches(".+\\.(png|jpg|jpeg)", file.getOriginalFilename())) {
                handleFile(file, recipe);
            } else {
                model.addAttribute("fileError", "Submit PNG or JPG picture please!");
                return "recipe-form";
            }
        }

        if (recipe.getId() == null) {
            recipeService.addRecipe(recipe);
        } else {
            recipeService.updateRecipe(recipe);
        }

        return "redirect:/recipes";
    }

    private void handleFile(MultipartFile file, Recipe recipe) {
        String oldName = recipe.getPhoto();
        if (oldName != null && oldName.length() > 0) { //delete old image file
            Path oldPath = Paths.get(getUploadsDir(), oldName).toAbsolutePath();
            if (Files.exists(oldPath)) {
                try {
                    Files.delete(oldPath);
                } catch (IOException ex) {
                }
            }
        }
        if (file != null && file.getOriginalFilename().length() > 4) {
            String newName = file.getOriginalFilename();
            Path newPath = Paths.get(getUploadsDir(), newName).toAbsolutePath();
            int n = 0;
            String finalName = newName;
            while (Files.exists(newPath)) {   // change destination file name if it already exists
                finalName = newName.substring(0, newName.length() - 4) + "_" + ++n + newName.substring(newName.length() - 4);
                newPath = Paths.get(getUploadsDir(), finalName).toAbsolutePath();
            }
            try {
                FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(newPath));
                recipe.setPhoto(finalName);
            } catch (IOException ex) {
            }
        }
    }

    protected String getUploadsDir() {
        File uploadsDir = new File(this.uploadsDir);
        if (!uploadsDir.exists()) {
            uploadsDir.mkdirs();
        }
        return uploadsDir.getAbsolutePath();
    }
}
