package com.devandre.moviebox.user.application.port.in.query;

import com.devandre.moviebox.user.domain.model.User;
import com.devandre.moviebox.user.domain.vo.UserPublicId;

public interface GetUserCase {

    User getUser(UserPublicId userPublicId);
    User getUserByEmail(String email);
}
