package com.devandre.moviebox.movie.application.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateMovieRequest(
        @NotNull String name,
        @NotNull Integer releaseYear,
        @NotNull String synopsis,
        @NotNull String posterURL,
        @NotNull Long categoryId,
        @NotNull UUID moviePublicId
) {
}
