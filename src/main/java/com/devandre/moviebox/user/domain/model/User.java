package com.devandre.moviebox.user.domain.model;

import com.devandre.moviebox.user.domain.vo.UserEmail;
import com.devandre.moviebox.user.domain.vo.UserPassword;
import com.devandre.moviebox.user.domain.vo.UserPublicId;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class User {

    private Long dbId;
    private String lastName;
    private String firstName;
    private UserEmail email;
    private UserPassword password;
    private String imageURL;
    private UserPublicId userPublicId;
    private String address;
    private String documentNumber;
    private Boolean enabled;
    private Boolean nonLocked;
    private Boolean usingMfa;
    private Boolean isTwoFactorEnabled;
    private Set<Role> roles;

    public User() {
    }

    public User(Long dbId, String lastName, String firstName, UserEmail email, UserPassword password, String imageURL, UserPublicId userPublicId, String address, String documentNumber, Boolean enabled, Boolean nonLocked, Boolean usingMfa, Boolean isTwoFactorEnabled, Set<Role> roles) {
        this.dbId = dbId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.imageURL = imageURL;
        this.userPublicId = userPublicId;
        this.address = address;
        this.documentNumber = documentNumber;
        this.enabled = enabled;
        this.nonLocked = nonLocked;
        this.usingMfa = usingMfa;
        this.isTwoFactorEnabled = isTwoFactorEnabled;
        this.roles = roles;
    }

    public void initDefaultFields() {
        this.userPublicId = new UserPublicId(UUID.randomUUID());
    }
}
