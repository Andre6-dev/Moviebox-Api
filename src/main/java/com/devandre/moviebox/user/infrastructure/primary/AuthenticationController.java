package com.devandre.moviebox.user.infrastructure.primary;

import com.devandre.moviebox.shared.application.constants.ProjectConstants;
import com.devandre.moviebox.shared.infrastructure.ResponseHandler;
import com.devandre.moviebox.user.application.dto.request.AuthenticateUserRequest;
import com.devandre.moviebox.user.application.dto.request.CreateUserRequest;
import com.devandre.moviebox.user.application.port.in.command.AuthenticateUserCommand;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ProjectConstants.API_V1_AUTHENTICATION)
public class AuthenticationController {

    private final AuthenticateUserCommand authenticateUserCommand;

    public AuthenticationController(AuthenticateUserCommand authenticateUserCommand) {
        this.authenticateUserCommand = authenticateUserCommand;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody CreateUserRequest request) {
        return ResponseHandler.generateResponse(HttpStatus.CREATED, authenticateUserCommand.register(request), true);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody AuthenticateUserRequest request) {
        return ResponseHandler.generateResponse(HttpStatus.CREATED, authenticateUserCommand.authenticate(request), true);
    }

    @GetMapping("/activate")
    public ResponseEntity<Object> activate(
            @RequestParam @Pattern(regexp = "^\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}$", message = "Invalid activation code")
            String activationCode
    ) {
        return ResponseHandler.generateResponse(HttpStatus.OK, authenticateUserCommand.activate(activationCode), true);
    }
}
