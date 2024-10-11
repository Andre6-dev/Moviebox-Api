package com.devandre.moviebox.user.domain.vo;

import java.util.UUID;

public record UserPublicId(UUID value) {

    // Assert
    public UserPublicId {
        if (value == null) {
            throw new IllegalArgumentException("value cannot be null");
        }
    }
}
