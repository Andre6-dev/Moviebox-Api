package com.devandre.moviebox.user.application.port.in.command;

import com.devandre.moviebox.user.application.dto.request.AuthenticateUserRequest;
import com.devandre.moviebox.user.application.dto.request.CreateUserRequest;
import com.devandre.moviebox.user.application.dto.response.ActivationCodeResponse;
import com.devandre.moviebox.user.application.dto.response.AuthenticateUserResponse;

public interface AuthenticateUserCommand {

    ActivationCodeResponse register(CreateUserRequest request);
    AuthenticateUserResponse authenticate(AuthenticateUserRequest request);
    ActivationCodeResponse activate(String key);
}
