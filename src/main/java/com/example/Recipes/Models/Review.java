package com.example.Recipes.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
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
    @Column(nullable = false)
    private String username;


    @ManyToOne(optional = false)
    @JoinColumn
    @JsonIgnore
    private CustomUserDetails user;
    public String getAuthor() {
        return user.getUsername();
    }

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


