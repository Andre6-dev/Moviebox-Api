package com.devandre.moviebox.user.infrastructure.secondary.adapter;

import com.devandre.moviebox.shared.infrastructure.security.jwt.JwtUtils;
import com.devandre.moviebox.user.application.port.out.TokenPersistencePort;
import com.devandre.moviebox.user.domain.model.Token;
import com.devandre.moviebox.user.domain.model.User;
import com.devandre.moviebox.user.infrastructure.secondary.mapper.TokenMapper;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.JpaTokenRepository;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.enums.TokenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TokenRepositoryAdapter implements TokenPersistencePort {

    private final JpaTokenRepository jpaTokenRepository;
    private final TokenMapper tokenMapper;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    public TokenRepositoryAdapter(JpaTokenRepository jpaTokenRepository, TokenMapper tokenMapper, JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jpaTokenRepository = jpaTokenRepository;
        this.tokenMapper = tokenMapper;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void createToken(User user, String jwt) {
        jpaTokenRepository.save(
                tokenMapper.mapToEntity(
                        Token.builder()
                                .jwt(jwt)
                                .user(user)
                                .tokenType(TokenType.BEARER)
                                .expirationDate(null)
                                .expired(false)
                                .revoked(false)
                                .build()
                )
        );
    }

    @Override
    public void deleteTokenByUser(User user) {
        jpaTokenRepository.findByUser_Id(user.getDbId())
                .ifPresent(token -> {
                    log.info("Deleting token with id and userEmail: {} {}", token.getUser().getId(), user.getEmail());
                    jpaTokenRepository.delete(token);
                    jpaTokenRepository.flush();
                });
    }

    @Override
    public String isTokenValid(String jwt) {
        UserDetails userDetails = extractUserDetails(jwt);
        boolean isTokenValid = jpaTokenRepository.findByJwt(jwt)
                .map(token -> !token.isExpired() && !token.isRevoked())
                .orElse(false);
        if (isTokenValid && jwtUtils.validateJwtToken(jwt)) {
            return userDetails.getUsername();
        } else {
            throw new RuntimeException("Invalid token");
        }
    }

    private UserDetails extractUserDetails(String jwt) {
        String email = jwtUtils.getEmailFromToken(jwt);
        return userDetailsService.loadUserByUsername(email);
    }
}
