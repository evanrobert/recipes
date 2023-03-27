package com.example.Recipes.Controller;


import com.example.Recipes.Exceptions.NoSuchRecipeException;
import com.example.Recipes.Exceptions.NoSuchReviewException;
import com.example.Recipes.Models.CustomUserDetails;
import com.example.Recipes.Models.Recipe;
import com.example.Recipes.Models.Review;
import com.example.Recipes.Service.RecipeService;
import com.example.Recipes.Service.ReviewService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/review")
    public class ReviewController {


    @Autowired
    ReviewService reviewService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable("id") Long id) {
        try {
            Review retrievedReview = reviewService.getReviewById(id);
            return ResponseEntity.ok(retrievedReview);
        } catch (IllegalStateException | NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<?> getReviewByRecipeId(@PathVariable("recipeId") Long recipeId) {
        try {
            List<Review> reviews = reviewService.getReviewByRecipeId(recipeId);
            return ResponseEntity.ok(reviews);
        } catch (NoSuchRecipeException | NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getReviewByUsername(@PathVariable("username") String username) {
        try {
            List<Review> reviews = reviewService.getReviewByUsername(username);
            return ResponseEntity.ok(reviews);
        } catch (NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/{recipeId}")
//    public ResponseEntity<?> postNewReview(@RequestBody Review review, @PathVariable("recipeId") Long recipeId) {
//        try {
//            Recipe insertedRecipe = reviewService.postNewReview(review, recipeId);
//            return ResponseEntity.created(insertedRecipe.getLocationURI()).body(insertedRecipe);
//        } catch (NoSuchRecipeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteReviewById(@PathVariable("id") Long id) {
//        try {
//            Review review = reviewService.deleteReviewById(id);
//            return ResponseEntity.ok(review);
//        } catch (NoSuchReviewException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    @PatchMapping
//    public ResponseEntity<?> updateReviewById(@RequestBody Review reviewToUpdate) {
//        try {
//            Review review = reviewService.updateReviewById(reviewToUpdate);
//            return ResponseEntity.ok(review);
//        } catch (NoSuchReviewException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    //QUESTION 6
    @GetMapping("reviews/{userName}")
    public List<Review> getRecipeByUsername(@PathVariable String userName) throws NoSuchReviewException {
        List<Review> reviews = reviewService.getReviewByUsername(userName);
        return reviews;
    }
//Question 7
    @PostMapping("/recipes/{recipeId}/reviews")
    public ResponseEntity<String> submitReview(@PathVariable Long recipeId, @RequestBody Review review) throws NoSuchReviewException {
        if (review.getRating() == recipeId) {
            throw new NoSuchReviewException("you cant review your own recipe");
        } else if (review.getRating() != recipeId) {
        }
        return (ResponseEntity<String>) ResponseEntity.ok("Review submitted successfully!");

        }
    @PostMapping("/{recipeId}")
    public ResponseEntity<?> postNewReview(@RequestBody Review review,
                                           @PathVariable("recipeId") Long recipeId, Authentication authentication) {
        try {
            review.setUser((CustomUserDetails) authentication.getPrincipal());
            Recipe insertedRecipe = reviewService.postNewReview(review, recipeId);
            return ResponseEntity.created(insertedRecipe.getLocationURI()).body(insertedRecipe);
        } catch (NoSuchRecipeException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'Review', 'delete')")
    public ResponseEntity<?> deleteReviewById(@PathVariable("id")Long id) {
        try {
            Review review = reviewService.deleteReviewById(id);
            return ResponseEntity.ok(review);
        } catch (NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping
    @PreAuthorize("hasPermission(#reviewToUpdate.id, 'Review', 'edit')")
    public ResponseEntity<?> updateReviewById(@RequestBody Review reviewToUpdate) {
        try {
            Review review = reviewService.updateReviewById(reviewToUpdate);
            return ResponseEntity.ok(review);
        } catch (NoSuchReviewException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}





