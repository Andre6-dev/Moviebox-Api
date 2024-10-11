package com.devandre.moviebox.user.application.port.in.query;

import com.devandre.moviebox.user.domain.model.Role;

import java.util.List;
import java.util.Set;

public interface GetRoleUseCase {

    List<Role> getRoles();
}
