package com.devandre.moviebox.movie.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record RemoveRatingRequest(
        @NotNull Long movieId,
        @NotNull Long userId
) {
}
