package com.devandre.moviebox.movie.infrastructure.secondary.mapper;

import com.devandre.moviebox.movie.domain.model.Movie;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.MovieEntity;
import com.devandre.moviebox.user.infrastructure.secondary.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MovieMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;

    public MovieMapper(CategoryMapper categoryMapper, UserMapper userMapper) {
        this.categoryMapper = categoryMapper;
        this.userMapper = userMapper;
    }

    public Movie mapToDomain(MovieEntity movieEntity) {
        return Movie.builder()
                .id(movieEntity.getId())
                .name(movieEntity.getName())
                .releaseYear(movieEntity.getReleaseYear())
                .synopsis(movieEntity.getSynopsis())
                .posterUrl(movieEntity.getPosterURL())
                .category(categoryMapper.mapToDomain(movieEntity.getCategory()))
                .createdBy(userMapper.mapToDomain(movieEntity.getCreatedBy()))
                .createdAt(movieEntity.getCreatedDate())
                .build();
    }

    public MovieEntity mapToEntity(Movie movie) {
        if (movie == null) {
            return null;
        }

        return MovieEntity.builder()
                .name(movie.getName())
                .releaseYear(movie.getReleaseYear())
                .synopsis(movie.getSynopsis())
                .posterURL(movie.getPosterUrl())
                .category(categoryMapper.mapToEntity(movie.getCategory()))
                .createdBy(userMapper.mapToEntity(movie.getCreatedBy()))
                .build();
    }
}
