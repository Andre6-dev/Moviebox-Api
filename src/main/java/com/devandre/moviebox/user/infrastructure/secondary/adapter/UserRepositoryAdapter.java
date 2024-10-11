package com.devandre.moviebox.user.infrastructure.secondary.adapter;

import com.devandre.moviebox.user.application.port.out.UserPersistencePort;
import com.devandre.moviebox.user.domain.model.User;
import com.devandre.moviebox.user.domain.vo.UserEmail;
import com.devandre.moviebox.user.domain.vo.UserPublicId;
import com.devandre.moviebox.user.infrastructure.secondary.mapper.UserMapper;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.JpaUserRepository;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserPersistencePort {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository, UserMapper userMapper) {
        this.jpaUserRepository = jpaUserRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.mapToEntity(user);
        jpaUserRepository.saveAndFlush(userEntity);
        return userMapper.mapToDomain(userEntity);
    }

    @Override
    public Optional<User> get(UserPublicId userPublicId) {
        return jpaUserRepository.findByPublicId(userPublicId.value())
                .map(userMapper::mapToDomain);
    }

    @Override
    public Optional<User> getOneByEmail(UserEmail userEmail) {
        return jpaUserRepository.findByEmail(userEmail.value())
                .map(userMapper::mapToDomain);
    }

    @Override
    public void enableUser(UserEmail userEmail, boolean enabled) {
        jpaUserRepository.updateUserEnabledStatus(userEmail.value(), enabled);
    }

    @Override
    public boolean existsByEmail(UserEmail userEmail) {
        jpaUserRepository.existsByEmail(userEmail.value());
    }


}
