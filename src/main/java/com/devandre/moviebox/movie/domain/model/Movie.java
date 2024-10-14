package com.devandre.moviebox.movie.domain.model;

import com.devandre.moviebox.movie.domain.vo.MoviePublicId;
import com.devandre.moviebox.user.domain.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.time.Instant;

@Value
@Getter
@Builder
public class Movie {

    Long id;
    String name;
    MoviePublicId publicId;
    Integer releaseYear;
    String synopsis;
    String posterUrl;
    Category category;
    User createdBy;
    Instant createdAt;

}
