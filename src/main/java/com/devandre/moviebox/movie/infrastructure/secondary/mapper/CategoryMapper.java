package com.devandre.moviebox.movie.infrastructure.secondary.mapper;

import com.devandre.moviebox.movie.domain.model.Category;
import com.devandre.moviebox.movie.infrastructure.secondary.persistence.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category mapToDomain(CategoryEntity categoryJpaEntity) {
        return Category.builder()
                .id(categoryJpaEntity.getId())
                .name(categoryJpaEntity.getName())
                .build();
    }

    public CategoryEntity mapToEntity(Category category) {
        return CategoryEntity.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
