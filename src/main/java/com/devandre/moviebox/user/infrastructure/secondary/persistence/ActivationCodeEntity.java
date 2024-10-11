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
@Table(name = "activation_code")
@Entity
@Builder
public class ActivationCodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String key;

    private LocalDateTime expirationDate;

    public ActivationCodeEntity() {
    }

    public ActivationCodeEntity(Long id, UserEntity user, String key, LocalDateTime expirationDate) {
        this.id = id;
        this.user = user;
        this.key = key;
        this.expirationDate = expirationDate;
    }
}
