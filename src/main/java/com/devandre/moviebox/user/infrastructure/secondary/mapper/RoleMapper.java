package com.devandre.moviebox.user.infrastructure.secondary.mapper;

import com.devandre.moviebox.user.domain.model.Role;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoleMapper {

    public Role mapToDomain(RoleEntity roleEntity) {
        return Role.builder()
                .dbId(roleEntity.getId())
                .roleName(roleEntity.getRoleName())
                .permission(roleEntity.getPermission())
                .build();
    }

    public Set<Role> mapToDomain(Set<RoleEntity> roleEntities) {
        return roleEntities.stream()
                .map(this::mapToDomain)
                .collect(java.util.stream.Collectors.toSet());
    }

    public RoleEntity mapToEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getDbId())
                .roleName(role.getRoleName())
                .permission(role.getPermission())
                .build();
    }

    public Set<RoleEntity> mapToEntity(Set<Role> roles) {
        return roles.stream()
                .map(this::mapToEntity)
                .collect(java.util.stream.Collectors.toSet());
    }
}
