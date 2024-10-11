package com.devandre.moviebox.user.application.port.in.command;

import com.devandre.moviebox.user.application.dto.request.CreateUserRequest;
import com.devandre.moviebox.user.domain.model.User;

public interface CreateUserCommand {

    User registerUser(CreateUserRequest createUserRequest);
    void updateUserEnabledStatus(String userEmail, boolean enabled);
    boolean isEmailAlreadyExist(String email);
}
