package com.devandre.moviebox.movie.infrastructure.primary;

import com.devandre.moviebox.movie.application.dto.request.RateMovieRequest;
import com.devandre.moviebox.movie.application.dto.request.RemoveRatingRequest;
import com.devandre.moviebox.movie.application.port.in.MovieRatingUseCases;
import com.devandre.moviebox.shared.application.constants.ProjectConstants;
import com.devandre.moviebox.shared.infrastructure.ResponseHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ProjectConstants.API_V1_RATING)
public class MovieRatingController {

    private final MovieRatingUseCases movieRatingUseCases;

    public MovieRatingController(MovieRatingUseCases movieRatingUseCases) {
        this.movieRatingUseCases = movieRatingUseCases;
    }

    @PostMapping("/rate-movie")
    @Tag(name = "Movie Ratings", description = "Rate a movie")
    public ResponseEntity<Object> rateMovie(@RequestBody @Valid RateMovieRequest request) {
        movieRatingUseCases.rateMovie(request);
        return ResponseHandler.generateResponse(
                HttpStatus.OK,
                "Movie rated successfully",
                false
        );
    }

    @DeleteMapping("/remove-rating")
    @Tag(name = "Movie Ratings", description = "Remove a rating")
    public ResponseEntity<Object> removeRating(@RequestBody @Valid RemoveRatingRequest request) {
        movieRatingUseCases.removeRating(request);
        return ResponseHandler.generateResponse(
                HttpStatus.OK,
                "Rating removed successfully",
                false
        );
    }

    @GetMapping("/get-ratings-by-user/{userId}")
    @Tag(name = "Movie Ratings", description = "Get ratings by user")
    public ResponseEntity<Object> getRatings(@PathVariable Long userId) {
        return ResponseHandler.generateResponse(
                HttpStatus.OK,
                movieRatingUseCases.getRatings(userId),
                false
        );
    }
}
