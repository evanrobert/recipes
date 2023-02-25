package com.example.Recipes.Exceptions;

public class NoSuchReviewException extends Exception {
    public NoSuchReviewException(String message) {
        super(message);
    }

    public NoSuchReviewException() {
    }
}

