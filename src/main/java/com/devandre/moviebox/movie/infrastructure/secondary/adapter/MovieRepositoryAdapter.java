package com.devandre.moviebox.movie.infrastructure.secondary.adapter;

import com.devandre.moviebox.movie.application.port.out.MoviePersistencePort;
import com.devandre.moviebox.movie.domain.model.Movie;
import com.devandre.moviebox.movie.domain.model.MovieSearchCriteria;
import com.devandre.moviebox.movie.domain.vo.MoviePublicId;
import com.devandre.moviebox.movie.infrastructure.secondary.mapper.MovieMapper;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.JpaMovieRatingRepository;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.JpaMovieRepository;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.MovieEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MovieRepositoryAdapter implements MoviePersistencePort {

    private final JpaMovieRepository jpaMovieRepository;
    private final MovieMapper movieMapper;

    public MovieRepositoryAdapter(JpaMovieRepository jpaMovieRepository, MovieMapper movieMapper) {
        this.jpaMovieRepository = jpaMovieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public Page<Movie> searchMovies(MovieSearchCriteria criteria) {
        Specification<MovieEntity> spec = createSpecification(criteria);
        Sort sort = createSort(criteria);
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize(), sort);

        Page<MovieEntity> movieEntities = jpaMovieRepository.findAll(spec, pageable);
        List<Movie> movies = movieEntities.getContent().stream()
                .map(movieMapper::mapToDomain)
                .toList();

        return new PageImpl<>(movies, pageable, movieEntities.getTotalElements());
    }

    @Override
    public Movie createMovie(Movie movie) {
        MovieEntity movieEntity = movieMapper.mapToEntity(movie);
        jpaMovieRepository.saveAndFlush(movieEntity);
        return movieMapper.mapToDomain(movieEntity);
    }

    @Override
    public Optional<Movie> getMovie(MoviePublicId id) {
        return jpaMovieRepository.findByPublicId(id.value()).map(movieMapper::mapToDomain);
    }

    @Override
    public void updateMovie(Long id, String name, Integer releaseYear, String synopsis, String posterURL, Long categoryId) {
        jpaMovieRepository.updateMovie(id, name, releaseYear, synopsis, posterURL, categoryId);
    }

    @Override
    public void deleteMovie(MoviePublicId id) {
        jpaMovieRepository.deleteByPublicId(id.value());
    }

    @Override
    public boolean existsByName(String name) {
        return jpaMovieRepository.existsByNameIgnoreCase(name);
    }

    private Specification<MovieEntity> createSpecification(MovieSearchCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getSearchTerm() != null && !criteria.getSearchTerm().isEmpty()) {
                String likePattern = "%" + criteria.getSearchTerm().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), likePattern),
                        cb.like(cb.lower(root.get("synopsis")), likePattern)
                ));
            }

            if (criteria.getCategoryId() != null) {
                predicates.add(cb.equal(root.get("category").get("id"), criteria.getCategoryId()));
            }

            if (criteria.getReleaseYear() != null) {
                predicates.add(cb.equal(root.get("releaseYear"), criteria.getReleaseYear()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Sort createSort(MovieSearchCriteria criteria) {
//        String sortBy = criteria.getSortBy() != null ? criteria.getSortBy() : "createdAt";
        String sortBy = criteria.getSortBy() != null ? criteria.getSortBy() : "createdDate";
        Sort.Direction direction = "desc".equalsIgnoreCase(criteria.getSortDirection()) ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        if ("rating".equals(sortBy)) {
            // Custom sorting for average rating
            return Sort.by(direction, "ratings.rating").and(Sort.by(Sort.Direction.DESC, "id"));
        }

        return Sort.by(direction, sortBy);
    }
}
