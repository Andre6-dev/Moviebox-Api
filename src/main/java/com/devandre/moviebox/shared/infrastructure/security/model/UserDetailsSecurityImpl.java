package com.devandre.moviebox.shared.infrastructure.security.model;

import com.devandre.moviebox.user.infrastructure.secondary.persistence.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Data
public class UserDetailsSecurityImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String email;

    @JsonIgnore
    private String password;

    private Boolean isActivated;

    private Boolean is2faEnabled;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsSecurityImpl(Long id, String email, String password, Boolean isActivated, Boolean is2faEnabled, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isActivated = isActivated;
        this.is2faEnabled = is2faEnabled;
        this.authorities = authorities;
    }

    public static UserDetailsSecurityImpl build(UserEntity user) {
        // GrantedAuthority authority = new SimpleGrantedAuthority(user.getRoleJpaEntities().stream().findFirst().get().getRoleName());

        GrantedAuthority authority = user.getRoles().stream().findFirst()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .orElseThrow(() -> new IllegalArgumentException("User has no roles"));

        return new UserDetailsSecurityImpl(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                user.getIsTwoFactorEnabled(),
                List.of(authority)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsSecurityImpl user = (UserDetailsSecurityImpl) o;
        return Objects.equals(id, user.id);
    }
}
