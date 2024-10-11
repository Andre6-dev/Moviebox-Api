package com.devandre.moviebox.user.domain.vo;

public record UserPassword(String value) {

    public UserPassword {
        if (value == null || value.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
    }
}
