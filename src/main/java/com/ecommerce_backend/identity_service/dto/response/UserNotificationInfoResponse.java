package com.ecommerce_backend.identity_service.dto.response;

public record UserNotificationInfoResponse(
        Long userId,
        String firstName,
        String email
) {
}
