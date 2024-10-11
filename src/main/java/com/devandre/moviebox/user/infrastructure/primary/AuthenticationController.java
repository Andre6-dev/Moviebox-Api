package com.devandre.moviebox.user.infrastructure.primary;

import com.devandre.moviebox.shared.infrastructure.ResponseHandler;
import com.devandre.moviebox.user.application.dto.request.CreateUserRequest;
import com.devandre.moviebox.user.application.port.in.command.AuthenticateUserCommand;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticateUserCommand authenticateUserCommand;

    public AuthenticationController(AuthenticateUserCommand authenticateUserCommand) {
        this.authenticateUserCommand = authenticateUserCommand;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody CreateUserRequest request) {
        return ResponseHandler.generateResponse(HttpStatus.CREATED, authenticateUserCommand.register(request), true);
    }
}
