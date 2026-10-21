package com.ecommerce_backend.identity_service.dto.response;

import com.ecommerce_backend.identity_service.entity.Role;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role
) {
}
