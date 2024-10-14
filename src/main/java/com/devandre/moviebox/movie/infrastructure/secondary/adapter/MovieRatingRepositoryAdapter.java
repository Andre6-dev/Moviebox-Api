package com.devandre.moviebox.movie.infrastructure.secondary.adapter;

import com.devandre.moviebox.movie.application.port.out.MovieRatingPersistencePort;
import com.devandre.moviebox.movie.domain.model.MovieRating;
import com.devandre.moviebox.movie.infrastructure.secondary.mapper.MovieRatingMapper;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.JpaMovieRatingRepository;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.JpaMovieRepository;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.MovieEntity;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.MovieRatingEntity;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.JpaUserRepository;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieRatingRepositoryAdapter implements MovieRatingPersistencePort {

    private final JpaMovieRatingRepository movieRatingRepository;
    private final JpaUserRepository userRepository;
    private final JpaMovieRepository movieRepository;
    private final MovieRatingMapper movieRatingMapper;

    public MovieRatingRepositoryAdapter(JpaMovieRatingRepository movieRatingRepository, JpaUserRepository userRepository, JpaMovieRepository movieRepository, MovieRatingMapper movieRatingMapper) {
        this.movieRatingRepository = movieRatingRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.movieRatingMapper = movieRatingMapper;
    }

    @Override
    public void rateMovie(Long movieId, Long userId, int rating) {
        MovieEntity movieEntity = movieRepository.findById(movieId).orElseThrow();
        UserEntity userEntity = userRepository.findById(userId).orElseThrow();

        MovieRatingEntity movieRatingEntity = MovieRatingEntity.builder()
                .movie(movieEntity)
                .user(userEntity)
                .rating(rating)
                .build();

        movieRatingRepository.save(movieRatingEntity);
    }

    @Override
    public void removeRating(Long movieId, Long userId) {
        MovieRatingEntity movieRatingEntity = movieRatingRepository.findByMovie_IdAndUser_Id(movieId, userId)
                .orElseThrow(() -> new RuntimeException("Rating not found"));

        movieRatingRepository.delete(movieRatingEntity);
    }

    @Override
    public List<MovieRating> getAllMoviesRatingByUser(Long userId) {
        List<MovieRatingEntity> movieRatingEntities = movieRatingRepository.findAllByUser_Id(userId);
        return movieRatingEntities.stream()
                .map(movieRatingMapper::mapToDomain)
                .toList();
    }
}
