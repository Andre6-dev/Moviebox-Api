package com.devandre.moviebox.user.infrastructure.secondary.adapter;

import com.devandre.moviebox.shared.infrastructure.security.jwt.JwtUtils;
import com.devandre.moviebox.user.application.port.out.TokenCodePersistencePort;
import com.devandre.moviebox.user.domain.model.User;
import com.devandre.moviebox.user.infrastructure.secondary.mapper.TokenMapper;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.JpaTokenRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

public class TokenRepositoryAdapter implements TokenCodePersistencePort {

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

    }

    @Override
    public void deleteTokenByUser(User user) {

    }

    @Override
    public String isTokenValid(String jwt) {
        return "";
    }
}
