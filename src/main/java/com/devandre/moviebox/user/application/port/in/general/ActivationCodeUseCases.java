package com.devandre.moviebox.user.application.port.in.general;

import com.devandre.moviebox.user.domain.model.ActivationCode;
import com.devandre.moviebox.user.domain.model.User;

public interface ActivationCodeUseCases {

    ActivationCode getActivationCodeByKey(String key);
    void checkActivationCodeExpiration(ActivationCode activationCode);
    void deleteActivationCodeById(Long id);
    void sendNewActivationCode(User user);
}
