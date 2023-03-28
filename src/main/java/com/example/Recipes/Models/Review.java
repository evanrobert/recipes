package com.example.Recipes.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "recipe")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @ManyToOne(optional = false)
    @JoinColumn
    @JsonIgnore
    private CustomUserDetails user;


    @Column(nullable = false)
    private int rating;

    private String description;

    @ManyToOne
    @JoinColumn(
            name = "recipeId",
            nullable = false,
            foreignKey = @ForeignKey
    )
    @JsonIgnore
    private Recipe recipe;

    public void setRating(int rating) {
        if(rating <= 0 || rating > 10) {
            throw new IllegalStateException("Rating must be between 0 and 10");
        }
        this.rating = rating;
    }

    public String getAuthor() {
        return user.getUsername();
    }
}



