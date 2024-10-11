package com.devandre.moviebox.user.domain.model;

import com.devandre.moviebox.user.infrastructure.secondary.persistence.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private Long id;
    private User user;
    private TokenType tokenType;
    private String jwt;
    private boolean revoked;
    private boolean expired;
    private LocalDateTime expirationDate;
    private LocalDateTime creationDate;
}
