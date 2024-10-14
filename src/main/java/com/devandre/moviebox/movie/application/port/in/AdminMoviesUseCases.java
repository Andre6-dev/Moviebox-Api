package com.devandre.moviebox.movie.application.port.in;

import com.devandre.moviebox.movie.application.dto.request.CreateMovieRequest;
import com.devandre.moviebox.movie.application.dto.request.UpdateMovieRequest;
import com.devandre.moviebox.movie.domain.model.Category;
import com.devandre.moviebox.movie.domain.model.Movie;
import com.devandre.moviebox.movie.domain.vo.MoviePublicId;

import java.util.List;

public interface AdminMoviesUseCases {

    Movie createMovie(CreateMovieRequest movie);
    void updateMovie(UpdateMovieRequest movie);
    void deleteMovie(MoviePublicId id);
    List<Category> getAllCategories();
}
