package com.devandre.moviebox.user.application.dto.response;

import lombok.Builder;

@Builder
public record AuthenticateUserResponse(
        String jwt,
        String email
) {
}
