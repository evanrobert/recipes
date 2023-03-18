package com.example.Recipes.Controller;

import com.example.Recipes.Exceptions.NoSuchRecipeException;
import com.example.Recipes.Models.Recipe;

import com.example.Recipes.Service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @PostMapping
    public ResponseEntity<?> createNewRecipe(@RequestBody Recipe recipe) {
        try {
            Recipe insertedRecipe = recipeService.createNewRecipe(recipe);
            return ResponseEntity.created(insertedRecipe.getLocationURI()).body(insertedRecipe);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable("id") Long id) {
        try {
            Recipe recipe = recipeService.getRecipeById(id);
            return ResponseEntity.ok(recipe);
        } catch (NoSuchRecipeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllRecipes() {
        try {
            return ResponseEntity.ok(recipeService.getAllRecipes());
        } catch (NoSuchRecipeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> getRecipesByName(@PathVariable("name") String name) {
        try {
            ArrayList<Recipe> matchingRecipes = (ArrayList<Recipe>) recipeService.getRecipesByName(name);
            return ResponseEntity.ok(matchingRecipes);
        } catch (NoSuchRecipeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipeById(@PathVariable("id") Long id) {
        try {
            Recipe deletedRecipe = recipeService.deleteRecipeById(id);
            return ResponseEntity.ok("The recipe with ID " + deletedRecipe.getId() + " and name " + deletedRecipe.getName() + " was deleted");
        } catch (NoSuchRecipeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<?> updateRecipe(@RequestBody Recipe updatedRecipe) {
        try {
            Recipe returnedUpdatedRecipe = recipeService.updateRecipe(updatedRecipe, true);
            return ResponseEntity.ok(returnedUpdatedRecipe);
        } catch (NoSuchRecipeException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }







    //    Question 2
    @GetMapping("/minRating")
    public List<Recipe> getRecipeByMinimum(@RequestParam(required = false) double RecipeByMinimum) throws NoSuchRecipeException {
        List<Recipe> recipes = recipeService.getAllRecipes();
        return recipes.stream().filter(recipe -> {
            try {
                return recipeService.averageReview(recipe) >= RecipeByMinimum;
            } catch (NoSuchRecipeException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

    }

    //Question 3
    @GetMapping("/search/{name}/{rating}")
    public List<Recipe> getNameAndRating(@PathVariable String name, @PathVariable double rating) throws NoSuchRecipeException {
        List<Recipe> recipes = recipeService.getRecipesByName(name);
        List<Recipe> filter = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipeService.averageReview(recipe) >= rating) {
                filter.add(recipe);
            }
        }

        return filter;
    }

   // Question 6
//   @GetMapping("/search/{userName}")
//   public List<Recipe> getRecipeByUsername(@PathVariable String userName, @RequestParam(required = false) Double rating) throws NoSuchRecipeException {
//       List<Recipe> userRecipes = recipeService.getRecipeByUserName(userName);
//       if (rating != null) {
//           userRecipes = userRecipes.stream().filter(recipe -> {
//               try {
//                   return recipeService.averageReview(recipe) >= rating;
//               } catch (NoSuchRecipeException e) {
//                   throw new RuntimeException(e);
//               }
//           }).collect(Collectors.toList());
//       }
//       return userRecipes;
//   }

    }




