package com.devandre.moviebox.user.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActivationCodeApplicationService {

    private final ActivationCodePersistencePort activationCodePersistencePort;
}
