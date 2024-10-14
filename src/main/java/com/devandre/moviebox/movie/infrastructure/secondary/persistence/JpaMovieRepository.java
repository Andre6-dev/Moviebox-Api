package com.devandre.moviebox.movie.infrastructure.secondary.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaMovieRepository extends JpaRepository<MovieEntity, Long>, JpaSpecificationExecutor<MovieEntity> {

    Optional<MovieEntity> findByPublicId(UUID publicId);

    // Exists by name ignore case
    boolean existsByNameIgnoreCase(String name);

    @Modifying
    @Query("update MovieEntity m set m.name = :name, m.releaseYear = :releaseYear, m.synopsis = :synopsis, m.posterURL = :posterURL, m.category.id = :categoryId where m.id = :id")
    void updateMovie(Long id, String name, Integer releaseYear, String synopsis, String posterURL, Long categoryId);

    @Modifying
    @Query("delete from MovieEntity m where m.publicId = :publicId")
    void deleteByPublicId(UUID publicId);
}
