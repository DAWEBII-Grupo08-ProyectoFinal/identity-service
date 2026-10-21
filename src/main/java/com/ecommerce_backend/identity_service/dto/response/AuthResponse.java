package com.ecommerce_backend.identity_service.dto.response;

public record AuthResponse(
        String token,
        Long userId,
        String email
) {
}
