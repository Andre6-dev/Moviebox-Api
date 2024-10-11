package com.devandre.moviebox.user.application.port.out;

import com.devandre.moviebox.user.domain.model.Role;

import java.util.List;

public interface RolePersistencePort {

    Role getRoleByName(String name);
    List<Role> getRoles();
}
