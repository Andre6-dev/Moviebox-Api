package com.devandre.moviebox.movie.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MovieSearchCriteria {
    String searchTerm;
    Long categoryId;
    Integer releaseYear;
    String sortBy;
    String sortDirection;
    int page;
    int size;
}
