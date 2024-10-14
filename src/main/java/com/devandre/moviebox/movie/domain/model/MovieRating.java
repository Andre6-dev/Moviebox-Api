package com.devandre.moviebox.movie.domain.model;

import com.devandre.moviebox.user.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class MovieRating {

    Long id;
    Movie movie;
    User user;
    Integer rating;
}
