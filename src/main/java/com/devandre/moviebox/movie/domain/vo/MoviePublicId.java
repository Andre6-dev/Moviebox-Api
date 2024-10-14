package com.devandre.moviebox.movie.domain.vo;

import java.util.UUID;

public record MoviePublicId(UUID value) {

    public MoviePublicId {
        if (value == null) {
            throw new IllegalArgumentException("PublicId cannot be null");
        }
    }
}
