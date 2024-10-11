package com.devandre.moviebox.user.infrastructure.secondary.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPublicId(UUID publicId);
    boolean existsByEmail(String email);

    @Modifying
    @Query("UPDATE UserEntity u set u.enabled = :enabled where u.email = :email")
    void updateUserEnabledStatus(@Param(value = "email") String email,
                                 @Param(value = "enabled") boolean enabled);
}
