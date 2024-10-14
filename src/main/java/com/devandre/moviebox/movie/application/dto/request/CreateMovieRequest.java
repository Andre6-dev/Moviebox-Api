package com.devandre.moviebox.movie.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateMovieRequest(
        @NotNull @NotBlank String name,
        @NotNull Integer releaseYear,
        @NotNull String synopsis,
        @NotNull String posterURL,
        @NotNull Long categoryId,
        @NotNull Long createdBy,
        @NotNull @Min(value = 0) @Max(value = 10) Integer rating
        ) {
}
