package com.devandre.moviebox.movie.application.service;

import com.devandre.moviebox.movie.application.dto.request.CreateMovieRequest;
import com.devandre.moviebox.movie.application.dto.request.UpdateMovieRequest;
import com.devandre.moviebox.movie.application.dto.response.MoviesListResponse;
import com.devandre.moviebox.movie.application.port.in.AdminMoviesUseCases;
import com.devandre.moviebox.movie.application.port.in.GetMoviesUseCase;
import com.devandre.moviebox.movie.application.port.out.MoviePersistencePort;
import com.devandre.moviebox.movie.domain.model.Category;
import com.devandre.moviebox.movie.domain.model.Movie;
import com.devandre.moviebox.movie.domain.model.MovieSearchCriteria;
import com.devandre.moviebox.movie.domain.vo.MoviePublicId;
import com.devandre.moviebox.movie.infrastructure.secondary.mapper.CategoryMapper;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.CategoryEntity;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.JpaCategoryRepository;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.JpaMovieRatingRepository;
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
    private final JpaMovieRatingRepository jpaMovieRatingRepository;

    public MovieService(MoviePersistencePort moviePersistencePort, JpaCategoryRepository jpaCategoryRepository,
                        CategoryMapper categoryMapper, UserPersistencePort userPersistencePort, JpaMovieRatingRepository jpaMovieRatingRepository) {
        this.moviePersistencePort = moviePersistencePort;
        this.jpaCategoryRepository = jpaCategoryRepository;
        this.categoryMapper = categoryMapper;
        this.userPersistencePort = userPersistencePort;
        this.jpaMovieRatingRepository = jpaMovieRatingRepository;
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
    public Movie createMovie(CreateMovieRequest request) {
        log.info("Creating movie with title: {}", request.name());
        // validate movie
        if (moviePersistencePort.existsByName(request.name())) {
            log.error("Movie with title: {} already exists", request.name());
            throw new IllegalArgumentException("Movie with title: " + request.name() + " already exists");
        }

        CategoryEntity category = jpaCategoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + request.categoryId()));

        User user = userPersistencePort.findById(request.createdBy())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.createdBy()));

        Movie newMovie = Movie.builder()
                .name(request.name())
                .publicId(new MoviePublicId(UUID.randomUUID()))
                .releaseYear(request.releaseYear())
                .synopsis(request.synopsis())
                .posterUrl(request.posterURL())
                .category(categoryMapper.mapToDomain(category))
                .createdBy(user)
                .build();

        log.info("Saving movie with title: {}", newMovie.getName());

        return moviePersistencePort.createMovie(newMovie);
    }

    @Override
    @Transactional
    public void updateMovie(UpdateMovieRequest movie) {
        log.info("Updating movie with id: {}", movie.moviePublicId());

        Movie movieToUpdate = moviePersistencePort.getMovie(new MoviePublicId(movie.moviePublicId()))
                .orElseThrow(() -> new IllegalArgumentException("Movie not found with id: " + movie.moviePublicId()));

        CategoryEntity category = jpaCategoryRepository.findById(movieToUpdate.getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + movieToUpdate.getId()));

        User user = userPersistencePort.findById(movieToUpdate.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + movieToUpdate.getId()));

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
