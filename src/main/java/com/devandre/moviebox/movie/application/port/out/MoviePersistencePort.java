package com.devandre.moviebox.movie.application.port.out;

import com.devandre.moviebox.movie.application.dto.request.CreateMovieRequest;
import com.devandre.moviebox.movie.domain.model.Movie;
import com.devandre.moviebox.movie.domain.model.MovieSearchCriteria;
import com.devandre.moviebox.movie.domain.vo.MoviePublicId;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface MoviePersistencePort {

    Page<Movie> searchMovies(MovieSearchCriteria criteria);
    Movie createMovie(CreateMovieRequest request);
    Optional<Movie> getMovie(MoviePublicId id);
    void updateMovie(Long id, String name, Integer releaseYear, String synopsis, String posterURL, Long categoryId);
    void deleteMovie(MoviePublicId id);
    boolean existsByName(String name);
}
