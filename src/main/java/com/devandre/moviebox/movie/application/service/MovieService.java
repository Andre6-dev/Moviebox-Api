package com.devandre.moviebox.movie.application.service;

import com.devandre.moviebox.movie.application.dto.request.CreateMovieRequest;
import com.devandre.moviebox.movie.application.dto.request.UpdateMovieRequest;
import com.devandre.moviebox.movie.application.dto.response.MovieCreateResponse;
import com.devandre.moviebox.movie.application.dto.response.MoviesListResponse;
import com.devandre.moviebox.movie.application.port.in.AdminMoviesUseCases;
import com.devandre.moviebox.movie.application.port.in.GetMoviesUseCase;
import com.devandre.moviebox.movie.application.port.out.MoviePersistencePort;
import com.devandre.moviebox.movie.domain.model.Category;
import com.devandre.moviebox.movie.domain.model.Movie;
import com.devandre.moviebox.movie.domain.model.MovieRating;
import com.devandre.moviebox.movie.domain.model.MovieSearchCriteria;
import com.devandre.moviebox.movie.domain.vo.MoviePublicId;
import com.devandre.moviebox.movie.infrastructure.secondary.mapper.CategoryMapper;
import com.devandre.moviebox.movie.infrastructure.secondary.mapper.MovieRatingMapper;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.CategoryEntity;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.JpaCategoryRepository;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.JpaMovieRatingRepository;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.JpaMovieRepository;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.MovieRatingEntity;
import com.devandre.moviebox.user.application.port.out.UserPersistencePort;
import com.devandre.moviebox.user.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MovieService implements GetMoviesUseCase, AdminMoviesUseCases {

    private final MoviePersistencePort moviePersistencePort;
    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserPersistencePort userPersistencePort;

    public MovieService(MoviePersistencePort moviePersistencePort, JpaCategoryRepository jpaCategoryRepository,
                        CategoryMapper categoryMapper, UserPersistencePort userPersistencePort) {
        this.moviePersistencePort = moviePersistencePort;
        this.jpaCategoryRepository = jpaCategoryRepository;
        this.categoryMapper = categoryMapper;
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    @Transactional
    public Page<MoviesListResponse> searchMovies(MovieSearchCriteria criteria) {
        log.info("Searching movies with criteria: {}", criteria);
        Page<Movie> movies = moviePersistencePort.searchMovies(criteria);

        List<Long> movieIds = movies.getContent().stream()
                .map(Movie::getId)
                .toList();
        // map movies to MoviesListResponse
        return movies.map(MoviesListResponse::from);
    }

    @Override
    @Transactional
    public MovieCreateResponse createMovie(CreateMovieRequest request) {
        log.info("Creating movie with title: {}", request.name());
        // validate movie
        if (moviePersistencePort.existsByName(request.name())) {
            log.error("Movie with title: {} already exists", request.name());
            throw new IllegalArgumentException("Movie with title: " + request.name() + " already exists");
        }

        // Validate the request.rating to be between 1 and 5
        if (request.rating() < 1 || request.rating() > 10) {
            log.error("Rating must be between 1 and 5");
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Movie movie = moviePersistencePort.createMovie(request);

        return MovieCreateResponse.from(movie);
    }

    @Override
    @Transactional
    public void updateMovie(UpdateMovieRequest movie) {
        log.info("Updating movie with id: {}", movie.moviePublicId());

        Movie movieToUpdate = moviePersistencePort.getMovie(new MoviePublicId(movie.moviePublicId()))
                .orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + movie.moviePublicId()));

        CategoryEntity category = jpaCategoryRepository.findById(movie.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + movieToUpdate.getId()));

        moviePersistencePort.updateMovie(movieToUpdate.getId(), movie.name(), movie.releaseYear(),
                movie.synopsis(), movie.posterURL(), category.getId());
    }

    @Override
    @Transactional
    public void deleteMovie(MoviePublicId publicId) {
        log.info("Deleting movie with id: {}", publicId);
        moviePersistencePort.deleteMovie(publicId);
    }

    @Override
    public List<Category> getAllCategories() {
        log.info("Fetching all categories");
        return jpaCategoryRepository.findAll().stream()
                .map(categoryMapper::mapToDomain)
                .toList();
    }
}
