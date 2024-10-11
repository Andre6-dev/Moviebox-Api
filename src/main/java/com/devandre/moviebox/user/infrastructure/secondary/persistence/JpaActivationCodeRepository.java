package com.devandre.moviebox.user.infrastructure.secondary.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaActivationCodeRepository extends JpaRepository<ActivationCodeEntity, Long> {

    Optional<ActivationCodeEntity> findActivationCodeEntityByKey(String key);
    Optional<ActivationCodeEntity> findActivationCodeEntityByUser_Email(String email);
}
