package com.devandre.moviebox.movie.application.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreateMovieRequest(
        @NotNull @NotBlank String name,
        @Size(max = 4) Integer releaseYear,
        @NotNull String synopsis,
        @NotNull String posterURL,
        @NotNull Long categoryId,
        @NotNull Long createdBy,
        @NotNull @DecimalMax("10.0") @DecimalMin("0.0") BigDecimal rating
        ) {
}
