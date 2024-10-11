package com.devandre.moviebox.user.infrastructure.secondary.adapter;

import com.devandre.moviebox.user.application.port.out.ActivationCodePersistencePort;
import com.devandre.moviebox.user.domain.model.ActivationCode;
import com.devandre.moviebox.user.infrastructure.secondary.mapper.ActivationCodeMapper;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.JpaActivationCodeRepository;
import org.springframework.stereotype.Component;

@Component
public class ActivationCodeRepositoryAdapter implements ActivationCodePersistencePort {

    private final JpaActivationCodeRepository jpaActivationCodeRepository;
    private final ActivationCodeMapper activationCodeMapper;

    public ActivationCodeRepositoryAdapter(JpaActivationCodeRepository jpaActivationCodeRepository, ActivationCodeMapper activationCodeMapper) {
        this.jpaActivationCodeRepository = jpaActivationCodeRepository;
        this.activationCodeMapper = activationCodeMapper;
    }

    @Override
    public void saveActivationCode(ActivationCode activationCode) {
        jpaActivationCodeRepository.saveAndFlush(activationCodeMapper.mapToEntity(activationCode));
    }

    @Override
    public ActivationCode getActivationCodeByKey(String key) {
        return jpaActivationCodeRepository.findActivationCodeEntityByKey(key)
                .map(activationCodeMapper::mapToDomain)
                .orElse(null);
    }

    @Override
    public ActivationCode getActivationCodeByUserEmail(String email) {
        return jpaActivationCodeRepository.findActivationCodeEntityByUser_Email(email)
                .map(activationCodeMapper::mapToDomain)
                .orElse(null);
    }

    @Override
    public void deleteActivationCodeById(Long id) {
        jpaActivationCodeRepository.deleteById(id);
    }
}
