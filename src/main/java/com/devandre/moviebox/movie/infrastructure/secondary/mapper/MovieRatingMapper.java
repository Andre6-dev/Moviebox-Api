package com.devandre.moviebox.movie.infrastructure.secondary.mapper;

import com.devandre.moviebox.movie.domain.model.MovieRating;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.MovieRatingEntity;
import com.devandre.moviebox.user.infrastructure.secondary.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component
public class MovieRatingMapper {

    private final MovieMapper movieMapper;
    private final UserMapper userMapper;

    public MovieRatingMapper(MovieMapper movieMapper, UserMapper userMapper) {
        this.movieMapper = movieMapper;
        this.userMapper = userMapper;
    }

    public MovieRating mapToDomain(MovieRatingEntity movieRatingJpaEntity) {
        return MovieRating.builder()
                .id(movieRatingJpaEntity.getId())
                .movie(movieMapper.mapToDomain(movieRatingJpaEntity.getMovie()))
                .user(userMapper.mapToDomain(movieRatingJpaEntity.getUser()))
                .rating(movieRatingJpaEntity.getRating())
                .build();
    }

    public MovieRatingEntity mapToEntity(MovieRating movieRating) {
        return MovieRatingEntity.builder()
                .id(movieRating.getId())
                .movie(movieMapper.mapToEntity(movieRating.getMovie()))
                .user(userMapper.mapToEntity(movieRating.getUser()))
                .rating(movieRating.getRating())
                .build();
    }
}
