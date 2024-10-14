package com.devandre.moviebox.movie.infrastructure.secondary.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
