package com.devandre.moviebox.user.infrastructure.secondary.adapter;

import com.devandre.moviebox.user.application.port.out.RolePersistencePort;
import com.devandre.moviebox.user.domain.model.Role;
import com.devandre.moviebox.user.infrastructure.secondary.mapper.RoleMapper;
import com.devandre.moviebox.user.infrastructure.secondary.persistence.JpaRoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleRepositoryAdapter implements RolePersistencePort {

    private final JpaRoleRepository jpaRoleRepository;
    private final RoleMapper roleMapper;

    public RoleRepositoryAdapter(JpaRoleRepository jpaRoleRepository, RoleMapper roleMapper) {
        this.jpaRoleRepository = jpaRoleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Role getRoleByName(String name) {
        return jpaRoleRepository.findByRoleName(name)
                .map(roleMapper::mapToDomain)
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + name));
    }

    @Override
    public List<Role> getRoles() {
        return jpaRoleRepository.findAll()
                .stream()
                .map(roleMapper::mapToDomain)
                .toList();
    }
}
