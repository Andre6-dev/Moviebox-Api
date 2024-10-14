package com.devandre.moviebox.movie.application.dto.response;

import com.devandre.moviebox.movie.domain.model.Category;
import com.devandre.moviebox.movie.domain.model.Movie;
import com.devandre.moviebox.movie.domain.vo.MoviePublicId;

import java.time.Instant;

public record MoviesListResponse(
        Long id,
        String name,
        MoviePublicId publicId,
        Integer releaseYear,
        String synopsis,
        String posterUrl,
        Category category,
        String createdBy,
        Instant createdAt
) {
    public static MoviesListResponse from(Movie movie) {
        return new MoviesListResponse(
                movie.getId(),
                movie.getName(),
                movie.getPublicId(),
                movie.getReleaseYear(),
                movie.getSynopsis(),
                movie.getPosterUrl(),
                movie.getCategory(),
                movie.getCreatedBy().getEmail().value(),
                movie.getCreatedAt()
        );
    }
}
