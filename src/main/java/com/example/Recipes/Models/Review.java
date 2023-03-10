package com.example.Recipes.Models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    private int rating;

    @NotNull
    private String description;
//Question 8
    public void setRating(int rating) {
        if (rating <= 1 || rating > 10) {

            throw new IllegalStateException("Rating must be between 0 and 10.");
        }
        this.rating = rating;
    }
}


