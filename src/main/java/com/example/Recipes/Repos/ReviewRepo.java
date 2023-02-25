package com.example.Recipes.Repos;

import com.example.Recipes.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review,Long> {
    List<Review> findByUsername(String username);
}
