package com.devandre.moviebox.movie.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class Category {

    Long id;
    String name;
}
