package com.devandre.moviebox.user.application.port.in.query;

import com.devandre.moviebox.user.domain.model.User;
import com.devandre.moviebox.user.domain.vo.UserEmail;
import com.devandre.moviebox.user.domain.vo.UserPublicId;

import java.util.UUID;

public interface GetUserCase {

    User getUser(UserPublicId userPublicId);
    User getUserByEmail(UserEmail email);
}
