package com.devandre.moviebox.movie.application.service;

import com.devandre.moviebox.movie.application.dto.request.RateMovieRequest;
import com.devandre.moviebox.movie.application.dto.request.RemoveRatingRequest;
import com.devandre.moviebox.movie.application.dto.response.MovieRatingsListUserDto;
import com.devandre.moviebox.movie.application.port.in.MovieRatingUseCases;
import com.devandre.moviebox.movie.application.port.out.MovieRatingPersistencePort;
import com.devandre.moviebox.movie.domain.model.MovieRating;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class MovieRatingService implements MovieRatingUseCases {

    private final MovieRatingPersistencePort movieRatingPersistencePort;

    public MovieRatingService(MovieRatingPersistencePort movieRatingPersistencePort) {
        this.movieRatingPersistencePort = movieRatingPersistencePort;
    }

    @Override
    @Transactional
    public void rateMovie(RateMovieRequest request) {
        log.info("Rating movie with id: {} by user with id: {}", request.movieId(), request.userId());
        movieRatingPersistencePort.rateMovie(request.movieId(), request.userId(), request.rating());
    }

    @Override
    @Transactional
    public void removeRating(RemoveRatingRequest request) {
        log.info("Removing rating for movie with id: {} by user with id: {}", request.movieId(), request.userId());
        movieRatingPersistencePort.removeRating(request.movieId(), request.userId());
    }

    @Override
    @Transactional
    public List<MovieRatingsListUserDto> getRatings(Long userId) {
        log.info("Getting ratings for user with id: {}", userId);
        List<MovieRating> movieRatings = movieRatingPersistencePort.getAllMoviesRatingByUser(userId);

        return movieRatings.stream()
                .map(MovieRatingsListUserDto::from)
                .toList();
    }
}
