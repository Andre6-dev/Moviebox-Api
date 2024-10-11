package com.devandre.moviebox.user.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Role {

    private Long dbId;
    private String roleName;
    private String permission;

    public Role() {
    }

    public Role(Long dbId, String roleName, String permission) {
        this.dbId = dbId;
        this.roleName = roleName;
        this.permission = permission;
    }
}
