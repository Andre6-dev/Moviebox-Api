package com.devandre.moviebox.movie.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RateMovieRequest(
        @NotNull Long movieId,
        @NotNull Long userId,
        @NotNull @Min(0) @Max(10) Integer rating
) {
}
