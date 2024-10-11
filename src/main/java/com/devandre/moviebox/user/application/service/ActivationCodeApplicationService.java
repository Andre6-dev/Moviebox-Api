package com.devandre.moviebox.user.application.service;

import com.devandre.moviebox.shared.domain.error.ActivationCodeExpiredException;
import com.devandre.moviebox.user.application.port.in.general.ActivationCodeUseCases;
import com.devandre.moviebox.user.application.port.out.ActivationCodePersistencePort;
import com.devandre.moviebox.user.domain.model.ActivationCode;
import com.devandre.moviebox.user.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Slf4j
public class ActivationCodeApplicationService implements ActivationCodeUseCases {

    private final ActivationCodePersistencePort activationCodePersistencePort;
    private final EmailService emailService;

    public ActivationCodeApplicationService(ActivationCodePersistencePort activationCodePersistencePort, EmailService emailService) {
        this.activationCodePersistencePort = activationCodePersistencePort;
        this.emailService = emailService;
    }

    @Override
    public ActivationCode getActivationCodeByKey(String key) {
        return activationCodePersistencePort.getActivationCodeByKey(key);
    }

    @Override
    public void checkActivationCodeExpiration(ActivationCode activationCode) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = activationCode.getExpirationDate();
        if (expirationDate.isBefore(now)) {
            deleteActivationCodeById(activationCode.getId());
            sendNewActivationCode(activationCode.getUser());

            long minutes = ChronoUnit.MINUTES.between(expirationDate, now);

            throw new ActivationCodeExpiredException(
                    "Activation code expired. New activation code sent to email: " + activationCode.getUser().getEmail() +
                            ". Activation code expired for " + minutes + " minutes."
            );
        }
    }

    @Override
    public void deleteActivationCodeById(Long id) {
        activationCodePersistencePort.deleteActivationCodeById(id);
    }

    @Override
    public void sendNewActivationCode(User user) {
        log.info("Sending new activation code to user with email: {}", user.getEmail().value());
        ActivationCode activationCode = ActivationCode.builder()
                .user(user)
                .key(UUID.randomUUID().toString())
                .expirationDate(LocalDateTime.now().plusHours(2L))
                .build();

        emailService.sendActivationCode(activationCode);
        activationCodePersistencePort.saveActivationCode(activationCode);
    }
}
