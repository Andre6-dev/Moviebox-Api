package com.devandre.moviebox.movie.application.dto.response;

import com.devandre.moviebox.movie.domain.model.Category;
import com.devandre.moviebox.movie.domain.model.Movie;
import com.devandre.moviebox.movie.domain.vo.MoviePublicId;

public record MovieCreateResponse(
        Long id,
        String name,
        MoviePublicId publicId,
        Integer releaseYear,
        String synopsis,
        String posterUrl,
        Category category,
        Long createdBy
) {
    public static MovieCreateResponse from(Movie movie) {
        return new MovieCreateResponse(
                movie.getId(),
                movie.getName(),
                movie.getPublicId(),
                movie.getReleaseYear(),
                movie.getSynopsis(),
                movie.getPosterUrl(),
                movie.getCategory(),
                movie.getCreatedBy().getDbId()
        );
    }
}
