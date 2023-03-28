package com.example.Recipes.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Recipe {

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
    private String name;

    @Column(nullable = false)
    private Integer minutesToMake;

    @Column(nullable = false)
    private Integer difficultyRating;



    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "recipeId",
            nullable = false
    )
    private Collection<Ingredient> ingredients;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "recipeId",
            nullable = false,
            foreignKey = @ForeignKey
    )
    private Collection<Step> steps;

    @OneToMany(mappedBy = "recipe")
    private Collection<Review> reviews;

    @Transient
    @JsonIgnore
    private URI locationURI;

    public Recipe() {
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
    }

    public void setDifficultyRating(int difficultyRating) {

        if(difficultyRating < 0 || difficultyRating > 10) {
            throw new IllegalStateException("difficulty rating must be between 0 and 10");
        }

        this.difficultyRating = difficultyRating;
    }

    @PrePersist
    public void validate() throws IllegalStateException {
        if(ingredients.size() == 0) {
            throw new IllegalStateException("You have to have at least one ingredient for you recipe!");
        }else if(steps.size() == 0) {
            throw new IllegalStateException("You have to include at least one step for your recipe!");
        }
    }

    public void generateLocationURI() {
        try {
            locationURI = new URI(
                    ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/recipes/")
                            .path(String.valueOf(id))
                            .toUriString());
        }catch (URISyntaxException e) {
            //Exception should stop here.
        }
    }

    public String getAuthor() {
        return user.getUsername();
    }
}



