package com.example.Recipes.Repos;

import com.example.Recipes.Models.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<CustomUserDetails, Long> {

    CustomUserDetails findByUsername(String username);
}
