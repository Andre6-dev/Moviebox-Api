package com.devandre.moviebox.user.domain.vo;

public record UserEmail(String value) {

    public UserEmail {
        if (value == null || !value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
