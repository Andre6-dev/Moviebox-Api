package com.devandre.moviebox.user.infrastructure.secondary.persistence;

import com.devandre.moviebox.user.infrastructure.secondary.persistence.enums.TokenType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "token")
@Entity
@Builder
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    @Column(unique = true)
    private String jwt;

    private boolean revoked;

    private boolean expired;

    private LocalDateTime expirationDate;

    private LocalDateTime creationDate;

    public TokenEntity() {
    }

    public TokenEntity(Long id, UserEntity user, TokenType tokenType, String jwt, boolean revoked, boolean expired, LocalDateTime expirationDate, LocalDateTime creationDate) {
        this.id = id;
        this.user = user;
        this.tokenType = tokenType;
        this.jwt = jwt;
        this.revoked = revoked;
        this.expired = expired;
        this.expirationDate = expirationDate;
        this.creationDate = creationDate;
    }
}
