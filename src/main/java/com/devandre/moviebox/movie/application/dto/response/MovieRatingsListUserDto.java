package com.devandre.moviebox.movie.application.dto.response;

import com.devandre.moviebox.movie.domain.model.MovieRating;

public record MovieRatingsListUserDto(
        Long movieId,
        String title,
        Long userId,
        Integer rating
) {
    public static MovieRatingsListUserDto from(MovieRating movieRating) {
        return new MovieRatingsListUserDto(
                movieRating.getMovie().getId(),
                movieRating.getMovie().getName(),
                movieRating.getUser().getDbId(),
                movieRating.getRating()
        );
    }
}
