package com.devandre.moviebox.movie.infrastructure.secondary.persistence;

import com.devandre.moviebox.shared.infrastructure.persistence.AbstractAuditingEntity;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "movie_rating")
@Entity
@Builder
public class MovieRatingEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movieRatingSequenceGenerator")
    @SequenceGenerator(name = "movieRatingSequenceGenerator", sequenceName = "movie_rating_sequence", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private MovieEntity movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private Integer rating;

    public MovieRatingEntity() {
    }

    public MovieRatingEntity(Long id, MovieEntity movie, UserEntity user, Integer rating) {
        this.id = id;
        this.movie = movie;
        this.user = user;
        this.rating = rating;
    }
}
