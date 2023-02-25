package com.example.Recipes.Service;

import com.example.Recipes.Exceptions.NoSuchRecipeException;
import com.example.Recipes.Models.Recipe;
import com.example.Recipes.Models.Review;
import com.example.Recipes.Repos.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    RecipeRepo recipeRepo;


    @Transactional
    public Recipe createNewRecipe(Recipe recipe) throws IllegalStateException {
        recipe.validate();
        recipe = recipeRepo.save(recipe);
        recipe.generateLocationURI();
        return recipe;
    }

    public Recipe getRecipeById(Long id) throws NoSuchRecipeException {
        Optional<Recipe> recipeOptional = recipeRepo.findById(id);

        if (recipeOptional.isEmpty()) {
            throw new NoSuchRecipeException("No recipe with ID " + id + " could be found.");
        }

        Recipe recipe = recipeOptional.get();
        recipe.generateLocationURI();
        return recipe;
    }

    public List<Recipe> getRecipesByName(String name) throws NoSuchRecipeException {
        List<Recipe> matchingRecipes = recipeRepo.findByNameContainingIgnoreCase(name);

        if (matchingRecipes.isEmpty()) {
            throw new NoSuchRecipeException("No recipes could be found with that name.");
        }

        return matchingRecipes;
    }

    public List<Recipe> getAllRecipes() throws NoSuchRecipeException {
        List<Recipe> recipes = recipeRepo.findAll();

        if (recipes.isEmpty()) {
            throw new NoSuchRecipeException("There are no recipes yet :( feel free to add one though");
        }
        return recipes;
    }

    @Transactional
    public Recipe deleteRecipeById(Long id) throws NoSuchRecipeException {
        try {
            Recipe recipe = getRecipeById(id);
            recipeRepo.deleteById(id);
            return recipe;
        } catch (NoSuchRecipeException e) {
            throw new NoSuchRecipeException(e.getMessage() + " Could not delete.");
        }
    }

    @Transactional
    public Recipe updateRecipe(Recipe recipe, boolean forceIdCheck) throws NoSuchRecipeException {
        try {
            if (forceIdCheck) {
                getRecipeById(recipe.getId());
            }
            recipe.validate();
            Recipe savedRecipe = recipeRepo.save(recipe);
            savedRecipe.generateLocationURI();
            return savedRecipe;
        } catch (NoSuchRecipeException e) {
            throw new NoSuchRecipeException("The recipe you passed in did not have an ID found in the database." +
                    " Double check that it is correct. Or maybe you meant to POST a recipe not PATCH one.");
        }
    }
    public Double averageReview (Recipe recipe) throws NoSuchRecipeException{
        Collection<Review> reviews = recipe.getReviews();
        if(recipe.getReviews() == null || reviews.isEmpty()) {
            return (double) 0;

        } double totalRatings = 0;
        for(Review review: reviews){
            totalRatings+= review.getRating();
        }
        return totalRatings /reviews.size();

    }



}


























