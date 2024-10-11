package com.devandre.moviebox.user.application.port.out;

import com.devandre.moviebox.user.domain.model.User;

public interface TokenCodePersistencePort {

    void createToken(User user, String jwt);

    void deleteTokenByUser(User user);

    String isTokenValid(String jwt);
}
