package com.devandre.moviebox.user.infrastructure.secondary.persistence;

import com.devandre.moviebox.shared.infrastructure.persistence.AbstractAuditingEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Table(name = "moviebox_users")
@Entity
@Builder
public class UserEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
    @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1, initialValue = 5)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "public_id")
    private UUID publicId;

    @Column(name = "address")
    private String address;

    @Size(max = 20)
    @NotNull
    @Column(name = "document_number", nullable = false, length = 20)
    private String documentNumber;

    @Column(name = "is_enabled")
    private Boolean enabled;

    @Column(name = "non_locked")
    private Boolean nonLocked;

    @Column(name = "using_mfa")
    private Boolean usingMfa;

    @Column(name = "is_two_factor_enabled")
    private Boolean isTwoFactorEnabled = false;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "role_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(Long id, String lastName, String firstName, String email, String password, String imageURL, UUID publicId, String address, String documentNumber, Boolean enabled, Boolean nonLocked, Boolean usingMfa, Boolean isTwoFactorEnabled, Set<RoleEntity> roles) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.imageURL = imageURL;
        this.publicId = publicId;
        this.address = address;
        this.documentNumber = documentNumber;
        this.enabled = enabled;
        this.nonLocked = nonLocked;
        this.usingMfa = usingMfa;
        this.isTwoFactorEnabled = isTwoFactorEnabled;
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return Objects.equals(publicId, that.publicId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(publicId);
    }
}
