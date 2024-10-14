package com.devandre.moviebox.movie.infrastructure.secondary.persistence;

import com.devandre.moviebox.shared.infrastructure.persistence.AbstractAuditingEntity;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Table(name = "movies")
@Entity
@Builder
public class MovieEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movieSequenceGenerator")
    @SequenceGenerator(name = "movieSequenceGenerator", sequenceName = "movie_sequence", allocationSize = 1, initialValue = 21)
    private Long id;

    @Column(name = "movie_name", nullable = false)
    private String name;

    @Column(name = "public_id")
    private UUID publicId;

    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    @Column(name = "poster_url")
    private String posterURL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity createdBy;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieRatingEntity> ratings = new HashSet<>();

    // Helper method to add a rating
    public void addRating(MovieRatingEntity rating) {
        ratings.add(rating);
        rating.setMovie(this);
    }

    // Helper method to remove a rating
    public void removeRating(MovieRatingEntity rating) {
        ratings.remove(rating);
        rating.setMovie(null);
    }

    public MovieEntity() {
    }

    public MovieEntity(Long id, String name, UUID publicId, Integer releaseYear, String synopsis, String posterURL, CategoryEntity category, UserEntity createdBy, Set<MovieRatingEntity> ratings) {
        this.id = id;
        this.name = name;
        this.publicId = publicId;
        this.releaseYear = releaseYear;
        this.synopsis = synopsis;
        this.posterURL = posterURL;
        this.category = category;
        this.createdBy = createdBy;
        this.ratings = ratings;
    }
}
