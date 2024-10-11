package com.devandre.moviebox.user.application.dto.response;

import lombok.Builder;

@Builder
public record ActivationCodeResponse(
        String message
) {
}
