package com.devandre.moviebox.user.application.service;

import com.devandre.moviebox.user.application.port.in.query.GetRoleUseCase;
import com.devandre.moviebox.user.application.port.out.RolePersistencePort;
import com.devandre.moviebox.user.domain.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoleApplicationService implements GetRoleUseCase {

    private final RolePersistencePort rolePersistencePort;

    public RoleApplicationService(RolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public List<Role> getRoles() {
        log.info("Getting all roles");
        return rolePersistencePort.getRoles();
    }
}
