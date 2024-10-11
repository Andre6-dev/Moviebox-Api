package com.devandre.moviebox.user.infrastructure.secondary.mapper;

import com.devandre.moviebox.user.domain.model.ActivationCode;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.ActivationCodeEntity;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.JpaUserRepository;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class ActivationCodeMapper {

    private final UserMapper userMapper;
    private final JpaUserRepository jpaUserRepository;

    public ActivationCodeMapper(UserMapper userMapper, JpaUserRepository jpaUserRepository) {
        this.userMapper = userMapper;
        this.jpaUserRepository = jpaUserRepository;
    }

    public ActivationCode mapToDomain(ActivationCodeEntity activationCodeEntity) {
        return ActivationCode.builder()
                .id(activationCodeEntity.getId())
                .key(activationCodeEntity.getKey())
                .user(userMapper.mapToDomain(activationCodeEntity.getUser()))
                .expirationDate(activationCodeEntity.getExpirationDate())
                .build();
    }

    public ActivationCodeEntity mapToEntity(ActivationCode activationCode) {
        if (activationCode == null) {
            return null;
        }

        UserEntity userEntity = jpaUserRepository
                .findByEmail(activationCode.getUser().getEmail().value())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + activationCode.getUser().getEmail().value()));

        return ActivationCodeEntity.builder()
                .id(activationCode.getId())
                .key(activationCode.getKey())
                .user(userEntity)
                .expirationDate(activationCode.getExpirationDate())
                .build();
    }
}
