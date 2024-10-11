package com.devandre.moviebox.user.application.service;

import com.devandre.moviebox.user.application.port.in.command.TokenUseCases;
import com.devandre.moviebox.user.application.port.out.TokenPersistencePort;
import com.devandre.moviebox.user.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenApplicationService implements TokenUseCases {

    private final TokenPersistencePort tokenPort;

    public TokenApplicationService(TokenPersistencePort tokenPort) {
        this.tokenPort = tokenPort;
    }

    @Override
    public void createToken(User user, String jwt) {
        log.info("Creating token for user: {}", user.getEmail());
        tokenPort.createToken(user, jwt);
    }

    @Override
    public void deleteTokenByUser(User user) {
        log.info("Deleting token for user: {}", user.getEmail());
        tokenPort.deleteTokenByUser(user);
    }

    @Override
    public String isTokenValid(String jwt) {
        log.info("Checking if token is valid");
        return tokenPort.isTokenValid(jwt);
    }
}
