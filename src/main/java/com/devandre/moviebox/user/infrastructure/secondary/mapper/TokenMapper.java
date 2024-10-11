package com.devandre.moviebox.user.infrastructure.secondary.mapper;

import com.devandre.moviebox.user.domain.model.Token;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.JpaUserRepository;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.TokenEntity;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    private final UserMapper userMapper;
    private final JpaUserRepository userRepository;

    public TokenMapper(UserMapper userMapper, JpaUserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public Token mapToDomain(TokenEntity tokenJpaEntity) {
        return Token.builder()
                .id(tokenJpaEntity.getId())
                .jwt(tokenJpaEntity.getJwt())
                .user(userMapper.mapToDomain(tokenJpaEntity.getUser()))
                .revoked(tokenJpaEntity.isRevoked())
                .expired(tokenJpaEntity.isExpired())
                .expirationDate(tokenJpaEntity.getExpirationDate())
                .creationDate(tokenJpaEntity.getCreationDate())
                .build();
    }

    public TokenEntity mapToEntity(Token token) {

        UserEntity userEntity = userRepository.findById(token.getUser().getDbId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return TokenEntity.builder()
                .id(token.getId())
                .jwt(token.getJwt())
                .user(userEntity)
                .revoked(token.isRevoked())
                .expired(token.isExpired())
                .expirationDate(token.getExpirationDate())
                .creationDate(token.getCreationDate())
                .build();
    }
}
