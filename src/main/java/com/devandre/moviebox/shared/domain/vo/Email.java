package com.devandre.moviebox.shared.domain.vo;

public record Email(String value) {

    // use spring validation
    public Email {
        if (value == null || !value.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email");
        }
    }
}
