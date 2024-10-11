package com.devandre.moviebox.user.infrastructure.secondary.persistence;

import com.devandre.moviebox.user.domain.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Table(name = "role")
@Entity
@Builder
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleSequenceGenerator")
    @SequenceGenerator(name = "roleSequenceGenerator", sequenceName = "role_sequence", allocationSize = 1)
    @Column(name = "role_id")
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "role_name", nullable = false, length = 50)
    private String roleName;

    @Size(max = 255)
    @Column(name = "permission")
    private String permission;

    public RoleEntity() {
    }

    public RoleEntity(Long id, String roleName, String permission) {
        this.id = id;
        this.roleName = roleName;
        this.permission = permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(roleName, that.roleName) && Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName, permission);
    }
}
