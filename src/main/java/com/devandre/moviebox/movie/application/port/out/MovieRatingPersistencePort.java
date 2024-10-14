package com.devandre.moviebox.movie.application.port.out;

import com.devandre.moviebox.movie.domain.model.MovieRating;

import java.util.List;

public interface MovieRatingPersistencePort {

    void rateMovie(Long movieId, Long userId, int rating);
    void removeRating(Long movieId, Long userId);
    List<MovieRating> getAllMoviesRatingByUser(Long userId);
}
