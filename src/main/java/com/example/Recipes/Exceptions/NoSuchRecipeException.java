package com.example.Recipes.Exceptions;

public class NoSuchRecipeException extends Exception {
    public NoSuchRecipeException(String message) {
        super(message);
    }

    public NoSuchRecipeException() {
    }
}

