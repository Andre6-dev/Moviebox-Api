package com.devandre.moviebox.movie.application.port.in;

import com.devandre.moviebox.movie.application.dto.request.RateMovieRequest;
import com.devandre.moviebox.movie.application.dto.request.RemoveRatingRequest;
import com.devandre.moviebox.movie.application.dto.response.MovieRatingsListUserDto;
import com.devandre.moviebox.movie.domain.model.MovieRating;

import java.util.List;

public interface MovieRatingUseCases {

    void rateMovie(RateMovieRequest request);
    void removeRating(RemoveRatingRequest request);
    List<MovieRatingsListUserDto> getRatings(Long userId);
}
