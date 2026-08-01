package com.afran.auth_service.dto.response;

import java.time.LocalDateTime;

public record AuthResponse(

        String username,
        String email,
        String accessToken,
        LocalDateTime issuedAt
) {
}
