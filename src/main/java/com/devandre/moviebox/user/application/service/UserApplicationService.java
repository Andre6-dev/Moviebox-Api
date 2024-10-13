package com.devandre.moviebox.user.application.service;

import com.devandre.moviebox.user.application.dto.request.CreateUserRequest;
import com.devandre.moviebox.user.application.port.in.command.CreateUserCommand;
import com.devandre.moviebox.user.application.port.in.query.GetUserCase;
import com.devandre.moviebox.user.application.port.out.RolePersistencePort;
import com.devandre.moviebox.user.application.port.out.UserPersistencePort;
import com.devandre.moviebox.user.domain.model.Role;
import com.devandre.moviebox.user.domain.model.User;
import com.devandre.moviebox.user.domain.vo.UserEmail;
import com.devandre.moviebox.user.domain.vo.UserPassword;
import com.devandre.moviebox.user.domain.vo.UserPublicId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;


@Service
@Slf4j
public class UserApplicationService implements GetUserCase, CreateUserCommand {

    private static final String defaultProfileUrl = "https://images.squarespace-cdn.com/content/v1/5eb48d3fef49df153d320521/1618032587947-LCJQDE1Q9CGKRRM774H2/Daft+Punk+Toy+Face+II+.jpg";

    private final UserPersistencePort userPersistencePort;
    private final RolePersistencePort rolePersistencePort;
    private final PasswordEncoder passwordEncoder;

    public UserApplicationService(UserPersistencePort userPersistencePort, RolePersistencePort rolePersistencePort, PasswordEncoder passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.rolePersistencePort = rolePersistencePort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getUser(UserPublicId userPublicId) {
        log.info("Getting user with public id: {}", userPublicId);
        return userPersistencePort.get(userPublicId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with public id: " + userPublicId));
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("Getting user with email: {}", email);
        return userPersistencePort.getOneByEmail(new UserEmail(email))
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + email));
    }

    @Override
    public User registerUser(CreateUserRequest createUserRequest) {
        log.info("Registering user with email: {}", createUserRequest.email());

        Role role = rolePersistencePort.getRoleByName("ROLE_USER");

        User userToRegister = User.builder()
                .lastName(createUserRequest.lastName())
                .firstName(createUserRequest.firstName())
                .email(new UserEmail(createUserRequest.email()))
                .password(new UserPassword(passwordEncoder.encode(createUserRequest.password())))
                .imageURL(defaultProfileUrl)
                .userPublicId(new UserPublicId(UUID.randomUUID()))
                .address(createUserRequest.address())
                .documentNumber(createUserRequest.documentNumber())
                .enabled(false) // By default, user is disabled
                .nonLocked(true)
                .usingMfa(false)
                .isTwoFactorEnabled(false)
                .roles(Set.of(role))
                .build();


        return userPersistencePort.save(userToRegister);
    }

    @Override
    public void updateUserEnabledStatus(String userEmail, boolean enabled) {
        log.info("Updating user enabled status with email: {}", userEmail);
        userPersistencePort.enableUser(new UserEmail(userEmail), enabled);
    }

    @Override
    public boolean isEmailAlreadyExist(String email) {
        log.info("Checking if email already exists: {}", email);
        return userPersistencePort.existsByEmail(new UserEmail(email));
    }

    @Override
    public boolean isDocumentNumberAlreadyExist(String documentNumber) {
        log.info("Checking if document number already exists: {}", documentNumber);
        return userPersistencePort.existsByDocumentNumber(documentNumber);
    }


}
