package com.devandre.moviebox.user.application.service;

import com.devandre.moviebox.shared.domain.error.InvalidCredentialsException;
import com.devandre.moviebox.shared.infrastructure.security.jwt.JwtUtils;
import com.devandre.moviebox.shared.infrastructure.security.model.UserDetailsSecurityImpl;
import com.devandre.moviebox.user.application.dto.request.AuthenticateUserRequest;
import com.devandre.moviebox.user.application.dto.request.CreateUserRequest;
import com.devandre.moviebox.user.application.dto.response.ActivationCodeResponse;
import com.devandre.moviebox.user.application.dto.response.AuthenticateUserResponse;
import com.devandre.moviebox.user.application.port.in.command.AuthenticateUserCommand;
import org.springframework.security.authentication.AuthenticationManager;
import com.devandre.moviebox.user.domain.model.ActivationCode;
import com.devandre.moviebox.user.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class AuthApplicationService implements AuthenticateUserCommand {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final TokenApplicationService tokenApplicationService;
    private final UserApplicationService userApplicationService;
    private final ActivationCodeApplicationService activationCodeApplicationService;

    public AuthApplicationService(JwtUtils jwtUtils, AuthenticationManager authenticationManager, TokenApplicationService tokenApplicationService, UserApplicationService userApplicationService, ActivationCodeApplicationService activationCodeApplicationService) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.tokenApplicationService = tokenApplicationService;
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

        if (userApplicationService.isDocumentNumberAlreadyExist(request.documentNumber())) {
            log.error("User with document number: {} already exists", request.documentNumber());
            throw new RuntimeException("User with document number: " + request.documentNumber() + " already exists");
        }

        User newUser = userApplicationService.registerUser(request);

        activationCodeApplicationService.sendNewActivationCode(newUser);
        log.info("Activation code sent to email: {}", request.email());

        return ActivationCodeResponse.builder()
                .message("Activation code sent to email: " + request.email())
                .build();
    }

    @Override
    @Transactional
    public AuthenticateUserResponse authenticate(AuthenticateUserRequest request) {
        Authentication authentication;
        log.info("Authenticating user with email: {}", request.email());
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (DisabledException e) {
            throw new DisabledException("User is disabled");
        }catch (BadCredentialsException e) {
            log.error("Invalid credentials for user with email: {}", request.email());
            throw new InvalidCredentialsException("Invalid credentials");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsSecurityImpl userDetails = (UserDetailsSecurityImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateTokenFromUsername(userDetails);

        // Transform int to long
        long expirationMillis = jwtUtils.getJwtExpirationMs();
        String formattedExpirationTime = Instant.ofEpochMilli(expirationMillis)
                // UTF8 time zone
                .atZone(ZoneId.of("UTC"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        User user = userApplicationService.getUserByEmail(userDetails.getUsername());

        tokenApplicationService.deleteTokenByUser(user);
        tokenApplicationService.createToken(user, jwt);
        log.info("User with email: {} authenticated", request.email());

        return AuthenticateUserResponse.builder()
                .jwt(jwt)
                .email(userDetails.getUsername())
                .expirationDate(formattedExpirationTime)
                .build();
    }

    @Override
    @Transactional
    public ActivationCodeResponse activate(String key) {
        ActivationCode activationCode = activationCodeApplicationService.getActivationCodeByKey(key);

        activationCodeApplicationService.checkActivationCodeExpiration(activationCode);
        log.info("Activation code is valid for user with email: {}", activationCode.getUser().getEmail().value());
        userApplicationService.updateUserEnabledStatus(activationCode.getUser().getEmail().value(), true);
        log.info("User with email: {} has been enabled", activationCode.getUser().getEmail().value());
        activationCodeApplicationService.deleteActivationCodeById(activationCode.getId());
        log.info("Activation code with id: {} has been deleted", activationCode.getId());

        log.info("User with email: {} has successfully activated, ", activationCode.getUser().getEmail().value());
        return ActivationCodeResponse.builder()
                .message("User with email: " + activationCode.getUser().getEmail().value() + " has successfully activated")
                .build();
    }
}
