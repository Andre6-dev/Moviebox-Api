package com.devandre.moviebox.user.infrastructure.secondary.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaTokenRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByUser_Id(Long userId);

    Optional<TokenEntity> findByJwt(String jwt);
}
