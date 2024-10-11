package com.devandre.moviebox.user.application.service;

import com.devandre.moviebox.shared.infrastructure.security.jwt.JwtUtils;
import com.devandre.moviebox.user.application.dto.request.AuthenticateUserRequest;
import com.devandre.moviebox.user.application.dto.request.CreateUserRequest;
import com.devandre.moviebox.user.application.dto.response.ActivationCodeResponse;
import com.devandre.moviebox.user.application.dto.response.AuthenticateUserResponse;
import com.devandre.moviebox.user.application.port.in.command.AuthenticateUserCommand;
import com.devandre.moviebox.user.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthApplicationService implements AuthenticateUserCommand {

    private final JwtUtils jwtUtils;
//    private final AuthenticationManager authenticationManager;
//    private final TokenApplicationService tokenApplicationService;
    private final UserApplicationService userApplicationService;
    private final ActivationCodeApplicationService activationCodeApplicationService;

    public AuthApplicationService(JwtUtils jwtUtils, UserApplicationService userApplicationService, ActivationCodeApplicationService activationCodeApplicationService) {
        this.jwtUtils = jwtUtils;
        this.userApplicationService = userApplicationService;
        this.activationCodeApplicationService = activationCodeApplicationService;
    }

    @Override
    public ActivationCodeResponse register(CreateUserRequest request) {
        log.info("Registering user with email: {}", request.email());

        if (userApplicationService.isEmailAlreadyExist(request.email())) {
            log.error("User with email: {} already exists", request.email());
            throw new RuntimeException("User with email: " + request.email() + " already exists");
        }

        User newUser = userApplicationService.registerUser(request);

        activationCodeApplicationService.sendNewActivationCode(newUser);
        log.info("Activation code sent to email: {}", request.email());

        return ActivationCodeResponse.builder()
                .message("Activation code sent to email: " + request.email())
                .build();
    }

    @Override
    public AuthenticateUserResponse authenticate(AuthenticateUserRequest request) {
        return null;
    }

    @Override
    public ActivationCodeResponse activate(String key) {
        return null;
    }
}
