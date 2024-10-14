package com.devandre.moviebox.movie.application.port.in;

import com.devandre.moviebox.movie.application.dto.response.MoviesListResponse;
import com.devandre.moviebox.movie.domain.model.MovieSearchCriteria;
import org.springframework.data.domain.Page;

public interface GetMoviesUseCase {
    Page<MoviesListResponse> searchMovies(MovieSearchCriteria criteria);
}
