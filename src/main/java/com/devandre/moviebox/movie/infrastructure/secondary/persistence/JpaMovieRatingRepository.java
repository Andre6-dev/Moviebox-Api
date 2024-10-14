package com.devandre.moviebox.movie.infrastructure.secondary.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaMovieRatingRepository extends JpaRepository<MovieRatingEntity, Long> {

    Optional<MovieRatingEntity> findByMovie_IdAndUser_Id(Long movieId, Long userId);
    boolean existsByMovieIdAndUserId(Long movieId, Long userId);

    List<MovieRatingEntity> findAllByUser_Id(Long userId);

    List<Long> findAllRatingsByMovie_Id(Long movieId);
}
