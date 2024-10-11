package com.devandre.moviebox.user.infrastructure.secondary.mapper;

import com.devandre.moviebox.user.domain.model.User;
import com.devandre.moviebox.user.domain.vo.UserEmail;
import com.devandre.moviebox.user.domain.vo.UserPassword;
import com.devandre.moviebox.user.domain.vo.UserPublicId;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.JpaRoleRepository;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final JpaRoleRepository jpaRoleRepository;
    private final RoleMapper roleMapper;

    public UserMapper(JpaRoleRepository jpaRoleRepository, RoleMapper roleMapper) {
        this.jpaRoleRepository = jpaRoleRepository;
        this.roleMapper = roleMapper;
    }

    public User mapToDomain(UserEntity userEntity) {
        return User.builder()
                .dbId(userEntity.getId())
                .lastName(userEntity.getLastName())
                .firstName(userEntity.getFirstName())
                .email(new UserEmail(userEntity.getEmail()))
                .password(new UserPassword(userEntity.getPassword()))
                .imageURL(userEntity.getImageURL())
                .userPublicId(new UserPublicId(userEntity.getPublicId()))
                .address(userEntity.getAddress())
                .documentNumber(userEntity.getDocumentNumber())
                .enabled(userEntity.getEnabled())
                .nonLocked(userEntity.getNonLocked())
                .usingMfa(userEntity.getUsingMfa())
                .isTwoFactorEnabled(userEntity.getIsTwoFactorEnabled())
                .roles(roleMapper.mapToDomain(userEntity.getRoles()))
                .build();
    }

    public UserEntity mapToEntity(User user) {
        if (user == null) {
            return null;
        }

        return  UserEntity.builder()
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .email(user.getEmail().value())
                .password(user.getPassword().value())
                .imageURL(user.getImageURL())
                .publicId(user.getUserPublicId().value())
                .address(user.getAddress())
                .documentNumber(user.getDocumentNumber())
                .enabled(user.getEnabled())
                .nonLocked(user.getNonLocked())
                .usingMfa(user.getUsingMfa())
                .isTwoFactorEnabled(user.getIsTwoFactorEnabled())
                .roles(roleMapper.mapToEntity(user.getRoles()))
                .build();
    }

}
