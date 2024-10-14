package com.devandre.moviebox.movie.infrastructure.primary;

import com.devandre.moviebox.movie.application.dto.request.CreateMovieRequest;
import com.devandre.moviebox.movie.application.dto.request.UpdateMovieRequest;
import com.devandre.moviebox.movie.application.dto.response.MoviesListResponse;
import com.devandre.moviebox.movie.application.port.in.AdminMoviesUseCases;
import com.devandre.moviebox.movie.application.port.in.GetMoviesUseCase;
import com.devandre.moviebox.movie.domain.model.Movie;
import com.devandre.moviebox.movie.domain.model.MovieSearchCriteria;
import com.devandre.moviebox.movie.domain.vo.MoviePublicId;
import com.devandre.moviebox.shared.application.constants.ProjectConstants;
import com.devandre.moviebox.shared.infrastructure.ResponseHandler;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(ProjectConstants.API_V1_MOVIES)
public class MovieController {

    private final GetMoviesUseCase getMoviesUseCase;
    private final AdminMoviesUseCases adminMoviesUseCases;

    public MovieController(GetMoviesUseCase getMoviesUseCase, AdminMoviesUseCases adminMoviesUseCases) {
        this.getMoviesUseCase = getMoviesUseCase;
        this.adminMoviesUseCases = adminMoviesUseCases;
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MoviesListResponse>> searchMovies(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        MovieSearchCriteria criteria = MovieSearchCriteria.builder()
                .searchTerm(searchTerm)
                .categoryId(categoryId)
                .releaseYear(releaseYear)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .page(page)
                .size(size)
                .build();

        Page<MoviesListResponse> movies = getMoviesUseCase.searchMovies(criteria);
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/create-movie")
    public ResponseEntity<Object>  createMovie(@RequestBody @Valid CreateMovieRequest movie) {
        return ResponseHandler.generateResponse(HttpStatus.CREATED, adminMoviesUseCases.createMovie(movie), true);
    }

    @PutMapping("/update-movie")
    public ResponseEntity<Object> updateMovie(@RequestBody @Valid UpdateMovieRequest movie) {
        adminMoviesUseCases.updateMovie(movie);
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, "Movie updated successfully", true);
    }

    @DeleteMapping("/delete-movie")
    public ResponseEntity<Object> deleteMovie(@RequestParam UUID id) {
        adminMoviesUseCases.deleteMovie(new MoviePublicId(id));
        return ResponseHandler.generateResponse(HttpStatus.NO_CONTENT, "Movie deleted successfully", true);
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getAllCategories() {
        return ResponseHandler.generateResponse(HttpStatus.OK, adminMoviesUseCases.getAllCategories(), true);
    }
}
