package com.devandre.moviebox.user.application.port.out;

import com.devandre.moviebox.user.domain.model.ActivationCode;

public interface ActivationCodePersistencePort {

    void saveActivationCode(ActivationCode activationCode);
    ActivationCode getActivationCodeByKey(String key);
    ActivationCode getActivationCodeByUserEmail(String email);
    void deleteActivationCodeById(Long id);
}
