package com.devandre.moviebox.user.application.port.out;

import com.devandre.moviebox.user.domain.model.User;
import com.devandre.moviebox.user.domain.vo.UserEmail;
import com.devandre.moviebox.user.domain.vo.UserPublicId;

import java.util.Optional;

public interface UserPersistencePort {
    User save(User user);
    Optional<User> get(UserPublicId userPublicId);
    Optional<User> getOneByEmail(UserEmail userEmail);
    void enableUser(UserEmail userEmail, boolean enabled);
    boolean existsByEmail(UserEmail userEmail);
    boolean existsByDocumentNumber(String documentNumber);
}
