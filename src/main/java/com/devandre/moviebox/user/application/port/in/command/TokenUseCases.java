package com.devandre.moviebox.user.application.port.in.command;

import com.devandre.moviebox.user.domain.model.User;

public interface TokenUseCases {

    void createToken(User user, String jwt);

    void deleteTokenByUser(User user);

    String isTokenValid(String jwt);
}
